package com.cssuwbcommunity.csuwbbot.discord.slash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("slashFactory")
public class SlashFactory {
    private final LeetcodeSlash leetcodeSlash;
    private final AskRedditSlash askRedditSlash;
    private final HackerrankSlash hackerrankSlash;
    @Autowired
    public SlashFactory(final LeetcodeSlash leetcodeSlash,
        final AskRedditSlash askRedditSlash,
        final HackerrankSlash hackerrankSlash) {
        this.askRedditSlash = askRedditSlash;
        this.leetcodeSlash = leetcodeSlash;
        this.hackerrankSlash = hackerrankSlash;
    }
    public SlashFunctionality getSlashFunctionality(final String name){
        switch(name){
            case "leetcode":
                return leetcodeSlash;
            case "askreddit":
                return askRedditSlash;
            case "hackerrank":
                return hackerrankSlash;
        }
        throw new IllegalArgumentException("Slash functionality factory does not contain: " + name);
    }
}
