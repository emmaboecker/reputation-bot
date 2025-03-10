package de.chojo.repbot.data.wrapper;

import org.jetbrains.annotations.PropertyKey;

import java.util.List;

public class MessageSettings {
    private boolean reactionActive;
    private boolean answerActive;
    private boolean mentionActive;
    private boolean fuzzyActive;
    private boolean embedActive;

    public MessageSettings() {
        reactionActive = true;
        answerActive = true;
        mentionActive = true;
        fuzzyActive = true;
        embedActive = true;
    }

    public MessageSettings(boolean reactionActive, boolean answerActive, boolean mentionActive, boolean fuzzyActive, boolean embedActive) {
        this.reactionActive = reactionActive;
        this.answerActive = answerActive;
        this.mentionActive = mentionActive;
        this.fuzzyActive = fuzzyActive;
        this.embedActive = embedActive;
    }

    public boolean isReactionActive() {
        return reactionActive;
    }

    public boolean isAnswerActive() {
        return answerActive;
    }

    public boolean isMentionActive() {
        return mentionActive;
    }

    public boolean isFuzzyActive() {
        return fuzzyActive;
    }

    public boolean isEmbedActive() {
        return embedActive;
    }

    public void embedActive(boolean embedActive) {
        this.embedActive = embedActive;
    }

    public void reactionActive(boolean reactionActive) {
        this.reactionActive = reactionActive;
    }

    public void answerActive(boolean answerActive) {
        this.answerActive = answerActive;
    }

    public void mentionActive(boolean mentionActive) {
        this.mentionActive = mentionActive;
    }

    public void fuzzyActive(boolean fuzzyActive) {
        this.fuzzyActive = fuzzyActive;
    }

    public String toLocalizedString(GuildSettings guildSettings) {
        var setting = List.of(
                getSetting("command.repSettings.embed.descr.byReaction", isReactionActive()),
                getSetting("command.repSettings.embed.descr.byAnswer", isAnswerActive()),
                getSetting("command.repSettings.embed.descr.byMention", isMentionActive()),
                getSetting("command.repSettings.embed.descr.byFuzzy", isFuzzyActive()),
                getSetting("command.repSettings.embed.descr.byEmbed", isEmbedActive()),
                getSetting("command.repSettings.embed.descr.emojidebug", guildSettings.generalSettings().isEmojiDebug())
        );

        return String.join("\n", setting);
    }

    private String getSetting(@PropertyKey(resourceBundle = "locale") String locale, Object object) {
        return String.format("$%s$: %s", locale, object);
    }
}
