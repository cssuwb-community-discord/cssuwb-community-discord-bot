import { Client, TextChannel } from "discord.js";
import { inject, injectable } from "inversify";
import { TYPES } from "../types";
import { LeetcodeProblemDownloader } from "../leetcode/leetcode-problem-downloader";
import { LeetcodeProblemSelector } from "../leetcode/leetcode-problem-selector";
import { AskRedditFetcher } from "../redditfetch/ask-reddit-fetcher";
import { scheduleJob } from "node-schedule";
@injectable()
export class DailyTaskService {
  //4:00PM UTC, 8:00AM PST
  private readonly dailyTimeString: string = "00 00 16 * * *";
  private readonly updateFileTimeString: string = "00 00 15 * * 1";
  private client: Client;
  private readonly askRedditChannel: string;
  private readonly leetcodeChannel: string;
  private leetcodeProblemDownloader: LeetcodeProblemDownloader;
  private leetcodeProblemSelector: LeetcodeProblemSelector;
  private askRedditFetcher: AskRedditFetcher;
  constructor(
    @inject(TYPES.Client) client: Client,
    @inject(TYPES.AskRedditChannel) askRedditChannel: string,
    @inject(TYPES.LeetcodeChannel) leetcodeChannel: string,
    @inject(TYPES.LeetcodeProblemDownloader)
    leetcodeProblemDownloader: LeetcodeProblemDownloader,
    @inject(TYPES.LeetcodeProblemSelector)
    leetcodeProblemSelector: LeetcodeProblemSelector,
    @inject(TYPES.AskRedditFetcher) askRedditFetcher: AskRedditFetcher
  ) {
    this.client = client;
    this.askRedditChannel = askRedditChannel;
    this.leetcodeChannel = leetcodeChannel;
    this.leetcodeProblemDownloader = leetcodeProblemDownloader;
    this.leetcodeProblemSelector = leetcodeProblemSelector;
    this.askRedditFetcher = askRedditFetcher;
  }
  public registerDailyTasks() {
    console.log(Date.now());
    scheduleJob(this.dailyTimeString, (fireTime) => {
      console.log(`Starting reddit post routine at ${fireTime}`);
      this.client.channels
        .fetch(this.askRedditChannel)
        .then((channel) => {
          const textChannel: TextChannel = channel as TextChannel;
          this.askRedditFetcher
            .fetchRedditEmbed()
            .then((embed) => {
              textChannel.send(embed);
            })
            .catch((err) => {
              throw err;
            });
        })
        .catch((err) => {
          console.error(
            "An error occured in posting the daily reddit question"
          );
          console.log(err);
        });
    });
    scheduleJob(this.dailyTimeString, (fireTime) => {
      console.log(`Starting leetcode post routine at ${fireTime}`);
      this.client.channels
        .fetch(this.leetcodeChannel)
        .then((channel) => {
          const textChannel: TextChannel = channel as TextChannel;
          this.leetcodeProblemSelector
            .selectProblem()
            .then((embed) => {
              textChannel.send(embed);
            })
            .catch((err) => {
              throw err;
            });
        })
        .catch((err) => {
          console.error(
            "An error occured in posting the daily leetcode question"
          );
          console.log(err);
        });
    });
    scheduleJob(this.updateFileTimeString, (fireTime) => {
      console.log(`Starting leetcode problem download routine at ${fireTime}`);
      this.leetcodeProblemDownloader
        .downloadParsedProblems()
        .then((filePath) => {
          console.log(`Leetcode problem file located at ${filePath}`);
        })
        .catch((err) => {
          console.error(
            "An error occured in downloading the leetcode problems"
          );
          console.log(err);
        });
    });
  }
}
