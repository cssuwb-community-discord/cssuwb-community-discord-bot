import { Message } from "discord.js";
import { inject, injectable } from "inversify";
import { TYPES } from "../types";
import { LeetcodeProblemDownloader } from "../leetcode/leetcode-problem-downloader";
import { LeetcodeProblemSelector } from "../leetcode/leetcode-problem-selector";
import { AskRedditFetcher } from "../redditfetch/ask-reddit-fetcher";

// Class to handle how bot should respond to valid user input
@injectable()
export class MessageResponder {
  private leetcodeProblemDownloader: LeetcodeProblemDownloader;
  private leetcodeProblemSelector: LeetcodeProblemSelector;
  private askRedditFetcher: AskRedditFetcher;
  constructor(
    @inject(TYPES.LeetcodeProblemDownloader)
    leetcodeProblemDownloader: LeetcodeProblemDownloader,
    @inject(TYPES.LeetcodeProblemSelector)
    leetcodeProblemSelector: LeetcodeProblemSelector,
    @inject(TYPES.AskRedditFetcher) askRedditFetcher: AskRedditFetcher
  ) {
    this.leetcodeProblemDownloader = leetcodeProblemDownloader;
    this.leetcodeProblemSelector = leetcodeProblemSelector;
    this.askRedditFetcher = askRedditFetcher;
  }

  // Directly handle responding to user here
  handle(message: Message): Promise<Message | Message[]> {
    return Promise.reject();
  }
}
