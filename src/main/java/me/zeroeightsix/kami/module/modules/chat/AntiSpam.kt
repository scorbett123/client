package me.zeroeightsix.kami.module.modules.chat

import me.zeroeightsix.kami.KamiMod
import me.zeroeightsix.kami.module.Module
import me.zeroeightsix.kami.setting.ModuleConfig.setting
import me.zeroeightsix.kami.util.text.*
import net.minecraft.util.text.TextComponentString
import net.minecraftforge.client.event.ClientChatReceivedEvent
import org.kamiblue.event.listener.listener
import java.util.concurrent.ConcurrentHashMap
import java.util.regex.Pattern

@Module.Info(
    name = "AntiSpam",
    category = Module.Category.CHAT,
    description = "Removes spam and advertising from the chat",
    showOnArray = false
)
object AntiSpam : Module() {
    private val mode = setting("Mode", Mode.REPLACE)
    private val replaceMode = setting("ReplaceMode", ReplaceMode.ASTERISKS, visibility = { mode.value == Mode.REPLACE }, description = "What to replace censored words with")
    private val page = setting("Page", Page.TYPE)

    /* Page One */
    private val discordLinks = setting("Discord", true, visibility = { page.value == Page.TYPE }, description = "Censor discord links")
    private val slurs = setting("Slurs", true, visibility = { page.value == Page.TYPE }, description = "Censor slurs")
    private val swears = setting("Swears", false, visibility = { page.value == Page.TYPE }, description = "Censor swear words")
    private val automated = setting("Automated", true, visibility = { page.value == Page.TYPE }, description = "Censor frequently used automated messages")
    private val ips = setting("ServerIps", true, visibility = { page.value == Page.TYPE }, description = "Censor server IP adresses")
    private val specialCharEnding = setting("SpecialEnding", true, visibility = { page.value == Page.TYPE }, description = "Censor messages with special characters at the end")
    private val specialCharBegin = setting("SpecialBegin", true, visibility = { page.value == Page.TYPE }, description = "Censor messages with special characters at the beginning")
    private val greenText = setting("GreenText", false, visibility = { page.value == Page.TYPE }, description = "Censor green text (This is a format that is normally used for storytelling or ridicule)")
    private val fancyChat = setting("FancyChat", false, visibility = { page.value == Page.TYPE }, description = "Remove messages that contain fancy chat")

    /* Page Two */
    private val aggressiveFiltering = setting("AggressiveFiltering", true, visibility = { page.value == Page.SETTINGS }, description = "Filter aggressively (Prefer false positive over false negative)")
    private val duplicates = setting("Duplicates", true, visibility = { page.value == Page.SETTINGS }, description = "Remove duplicate messages")
    private val duplicatesTimeout = setting("DuplicatesTimeout", 30, 1..600, 5, visibility = { duplicates.value && page.value == Page.SETTINGS }, description = "The maximum length of time between duplicate messages")
    private val filterOwn = setting("FilterOwn", false, visibility = { page.value == Page.SETTINGS }, description = "Censor your own messages")
    private val filterDMs = setting("FilterDMs", false, visibility = { page.value == Page.SETTINGS }, description = "Filter your Direct Messages to you")
    private val filterServer = setting("FilterServer", false, visibility = { page.value == Page.SETTINGS }, description = "Filter messages from the server")
    private val showBlocked = setting("ShowBlocked", ShowBlocked.LOG_FILE, visibility = { page.value == Page.SETTINGS }, description = "What to do with messages that have been blocked.")

    private enum class Mode {
        REPLACE, HIDE
    }

    @Suppress("unused")
    private enum class ReplaceMode(val redaction: String) {
        REDACTED("[redacted]"), ASTERISKS("****")
    }

    private enum class Page {
        TYPE, SETTINGS
    }

    @Suppress("unused")
    private enum class ShowBlocked {
        NONE, LOG_FILE, CHAT, BOTH
    }

    private val messageHistory = ConcurrentHashMap<String, Long>()
    private val settingMap = hashMapOf(
        greenText to SpamFilters.greenText,
        specialCharBegin to SpamFilters.specialBeginning,
        specialCharEnding to SpamFilters.specialEnding,
        automated to SpamFilters.ownsMeAndAll,
        automated to SpamFilters.thanksTo,
        discordLinks to SpamFilters.discordInvite,
        ips to SpamFilters.ipAddress,
        automated to SpamFilters.announcer,
        automated to SpamFilters.spammer,
        automated to SpamFilters.insulter,
        automated to SpamFilters.greeter,
        slurs to SpamFilters.slurs,
        swears to SpamFilters.swears
    )

    init {
        listener<ClientChatReceivedEvent> { event ->
            if (mc.player == null) return@listener

            messageHistory.values.removeIf { System.currentTimeMillis() - it > 600000 }

            if (duplicates.value && checkDupes(event.message.unformattedText)) {
                event.isCanceled = true
            }

            val pattern = isSpam(event.message.unformattedText)

            if (pattern != null) { // null means no pattern found
                if (mode.value == Mode.HIDE) {
                    event.isCanceled = true
                } else if (mode.value == Mode.REPLACE) {
                    event.message = TextComponentString(sanitize(event.message.formattedText, pattern, replaceMode.value.redaction))
                }
            }

            if (fancyChat.value) {
                val message = sanitizeFancyChat(event.message.unformattedText)
                if (message.trim { it <= ' ' }.isEmpty()) { // this should be removed if we are going for an intelligent de-fancy
                    event.message = TextComponentString(getUsername(event.message.unformattedText) + " [Fancychat]")
                }
            }
        }
    }

    override fun onDisable() {
        messageHistory.clear()
    }

    private fun sanitize(toClean: String, matcher: String, replacement: String): String {
        return if (!aggressiveFiltering.value) {
            toClean.replace("\\b$matcher|$matcher\\b".toRegex(), replacement) // only check for start or end of a word
        } else { // We might encounter the scunthorpe problem, so aggressive mode is off by default.
            toClean.replace(matcher.toRegex(), replacement)
        }
    }

    private fun isSpam(message: String): String? {
        return if (!filterOwn.value && isOwn(message)
                || !filterDMs.value && MessageDetection.Direct.ANY detect message
                || !filterServer.value && MessageDetection.Server.ANY detect message) {
            null
        } else {
            detectSpam(removeUsername(message))
        }
    }

    private fun detectSpam(message: String): String? {
        for ((key, value) in settingMap) {
            val pattern = findPatterns(value, message)
            if (key.value && pattern != null) {
                sendResult(key.name, message)
                return pattern
            }
        }
        return null
    }

    private fun removeUsername(username: String): String {
        return username.replace("<[^>]*> ".toRegex(), "")
    }

    private fun getUsername(rawMessage: String): String? {
        val matcher = Pattern.compile("<[^>]*>", Pattern.CASE_INSENSITIVE).matcher(rawMessage)
        return if (matcher.find()) {
            matcher.group()
        } else {
            rawMessage.substring(0, rawMessage.indexOf(">")) // a bit hacky
        }
    }

    private fun checkDupes(message: String): Boolean {
        var isDuplicate = false

        if (messageHistory.containsKey(message) && (System.currentTimeMillis() - messageHistory[message]!!) / 1000 < duplicatesTimeout.value) isDuplicate = true
        messageHistory[message] = System.currentTimeMillis()

        if (isDuplicate) {
            sendResult("Duplicate", message)
        }
        return isDuplicate
    }


    private fun isOwn(message: String): Boolean {
        /* mc.player is null when the module is being registered, so this matcher isn't added alongside the other FilterPatterns */
        val ownFilter = "^<" + mc.player.name + "> "
        return Pattern.compile(ownFilter, Pattern.CASE_INSENSITIVE).matcher(message).find()
    }

    private fun findPatterns(patterns: Array<String>, string: String): String? {
        var cString = string
        cString = cString.replace("<[^>]*> ".toRegex(), "") // remove username first
        for (pattern in patterns) {
            if (Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(cString).find()) {
                return pattern
            }
        }
        return null
    }

    private fun sanitizeFancyChat(toClean: String): String {
        // this has the potential to be intelligent and convert to ascii instead of just delete
        return toClean.replace("[^\\u0000-\\u007F]".toRegex(), "")
    }

    private fun sendResult(name: String, message: String) {
        if (showBlocked.value == ShowBlocked.CHAT || showBlocked.value == ShowBlocked.BOTH) MessageSendHelper.sendChatMessage("$chatName $name: $message")
        if (showBlocked.value == ShowBlocked.LOG_FILE || showBlocked.value == ShowBlocked.BOTH) KamiMod.LOG.info("$chatName $name: $message")
    }
}
