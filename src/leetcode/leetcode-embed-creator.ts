import { inject, injectable } from "inversify";
import {
  LeetcodeProblem,
  difficultyEnumToString
} from "./leetcode-problem";
import { Message, MessageEmbed } from "discord.js";
const leetcodeProblemLink: string = "https://leetcode.com/problems/";
@injectable()
export class LeetcodeEmbedCreator {
  createEmbed(problemObject: LeetcodeProblem): MessageEmbed {
      const leetcodeDifficultyString: string = difficultyEnumToString(
        problemObject.difficulty
      );
      let embed: MessageEmbed = new MessageEmbed()
        .setTitle(problemObject.problemName)
        .setURL(leetcodeProblemLink + problemObject.problemNameSlug)
        .setColor("#e9971d")
        .setFooter("Difficulty: " + leetcodeDifficultyString);
      return embed;
  }
}
