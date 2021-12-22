import { Message } from "discord.js";
import { PingFinder } from "./ping-finder";
import { inject, injectable } from "inversify";
import { TYPES } from "../types";
import { LeetcodeProblemDownloader } from "../leetcode/leetcode-problem-downloader";
import { LeetcodeProblemSelector } from "../leetcode/leetcode-problem-selector";

// Class to handle how bot should respond to valid user input
@injectable()
export class MessageResponder {
  private pingFinder: PingFinder;
  private leetcodeProblemDownloader: LeetcodeProblemDownloader;
  private leetcodeProblemSelector: LeetcodeProblemSelector;
  constructor(
    @inject(TYPES.PingFinder) pingFinder: PingFinder,
    @inject(TYPES.LeetcodeProblemDownloader)
    leetcodeProblemDownloader: LeetcodeProblemDownloader,
    @inject(TYPES.LeetcodeProblemSelector)
    leetcodeProblemSelector: LeetcodeProblemSelector
  ) {
    this.pingFinder = pingFinder;
    this.leetcodeProblemDownloader = leetcodeProblemDownloader;
    this.leetcodeProblemSelector = leetcodeProblemSelector;
  }

  // Directly handle responding to user here
  handle(message: Message): Promise<Message | Message[]> {
    if (this.pingFinder.isPing(message.content)) {
      return message.reply("pong!");
    }
    if (message.content == "leetcodedownload") {
      return this.leetcodeProblemDownloader
        .downloadParsedProblems()
        .then((filePath) => {
          return message.reply(filePath);
        })
        .catch((exception) => {
          return message.reply(exception);
        });
    }
    if (message.content == "leetcodefetch") {
      return this.leetcodeProblemSelector
        .selectProblem()
        .then((embed) => message.reply(embed))
        .catch((err) => message.reply(err));
    }
    return Promise.reject();
  }
}
