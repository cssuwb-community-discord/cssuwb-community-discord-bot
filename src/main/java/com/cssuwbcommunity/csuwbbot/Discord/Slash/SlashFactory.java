package com.cssuwbcommunity.csuwbbot.Discord.Slash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("slashFactory")
public class SlashFactory {
    private final LeetcodeSlash leetcodeSlash;
    private final AskRedditSlash askRedditSlash;

    @Autowired
    public SlashFactory(final LeetcodeSlash leetcodeSlash, final AskRedditSlash askRedditSlash) {
        this.askRedditSlash = askRedditSlash;
        this.leetcodeSlash = leetcodeSlash;
    }
    public SlashFunctionality getSlashFunctionality(final String name){
        switch(name){
            case "leetcode":
                return leetcodeSlash;
            case "askreddit":
                return askRedditSlash;
        }
        throw new IllegalArgumentException("Slash functionality factory does not contain: " + name);
    }
}
