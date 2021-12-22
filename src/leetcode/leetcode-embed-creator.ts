import { inject, injectable } from "inversify";
import {
  LeetcodeDifficulty,
  LeetcodeProblem,
  difficultyEnumToString,
} from "./leetcode-problem";
import { Message, MessageEmbed } from "discord.js";
const leetcodeProblemLink: string = "https://leetcode.com/problems/";
@injectable()
export class LeetcodeEmbedCreator {
  createEmbed(problemObject: LeetcodeProblem): Promise<MessageEmbed> {
    return new Promise<MessageEmbed>((resolve, reject) => {
      try {
        const leetcodeDifficultyString: string = difficultyEnumToString(
          problemObject.difficulty
        );
        let embed: MessageEmbed = new MessageEmbed()
          .setTitle(problemObject.problemName)
          .setURL(leetcodeProblemLink + problemObject.problemNameSlug)
          .setFooter("Difficulty: " + leetcodeDifficultyString);
        resolve(embed);
      } catch (exception) {
        reject(exception);
      }
    });
  }
}
