import { Message } from "discord.js";
import { PingFinder } from "./ping-finder";
import { inject, injectable } from "inversify";
import { TYPES } from "../types";
import { LeetcodeProblemDownloader } from "../leetcode/leetcode-problem-downloader";
import { LeetcodeProblemSelector } from "../leetcode/leetcode-problem-selector";
import { AskRedditFetcher } from "../ask-reddit-fetcher";

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
    @inject(TYPES.AskRedditFetcher) askRedditFetcher: AskRedditFetcher
  ) {
    this.pingFinder = pingFinder;
    this.leetcodeProblemDownloader = leetcodeProblemDownloader;
    this.leetcodeProblemSelector = leetcodeProblemSelector;
    this.askRedditFetcher = askRedditFetcher;
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
    if (message.content == "!qotd") {
      return this.askRedditFetcher
        .fetchRedditEmbed()
        .then((embed) => message.reply(embed));
    }
    return Promise.reject();
  }
}
