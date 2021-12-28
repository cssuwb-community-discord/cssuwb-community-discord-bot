import "reflect-metadata";
import { Container, interfaces } from "inversify";
import { TYPES } from "./types";
import { Bot } from "./bot";
import { Client } from "discord.js";
import { MessageResponder } from "./services/message-responder";
import { LeetcodeProblemDownloader } from "./leetcode/leetcode-problem-downloader";
import { LeetcodeProblemParser } from "./leetcode/leetcode-problem-parser";
import { LeetcodeProblemPicker } from "./leetcode/leetcode-problem-picker";
import { LeetcodeEmbedCreator } from "./leetcode/leetcode-embed-creator";
import { LeetcodeProblemSelector } from "./leetcode/leetcode-problem-selector";
import { RedditOAuthGenerator } from "./redditfetch/reddit-ouath-generator";
import { RedditFetchTop } from "./redditfetch/reddit-fetch-top";
import { RedditRandomPostGenerator } from "./redditfetch/reddit-random-post-generator";
import { RedditPostParser } from "./redditfetch/reddit-post-parser";
import { RedditEmbedCreator } from "./redditfetch/reddit-embed-creator";
import { AskRedditFetcher } from "./redditfetch/ask-reddit-fetcher";
import { DailyTaskService } from "./services/daily-task-service";
const customenv = require("dotenv").config().parsed;

// Dependency Injection Container
const container = new Container();

// Initialize Discord Bot objects
container.bind<Bot>(TYPES.Bot).to(Bot).inSingletonScope();
container.bind<Client>(TYPES.Client).toConstantValue(new Client());
container
  .bind<string>(TYPES.DiscordToken)
  .toConstantValue(process.env.DISCORD_TOKEN);
container
  .bind<DailyTaskService>(TYPES.DailyTaskService)
  .to(DailyTaskService)
  .inSingletonScope();

// Initialize Discord Bot functionality classes
container
  .bind<MessageResponder>(TYPES.MessageResponder)
  .to(MessageResponder)
  .inSingletonScope();

// Initialize Reddit credentials
container
  .bind<string>(TYPES.RedditUsername)
  .toConstantValue(process.env.REDDIT_USERNAME);
container
  .bind<string>(TYPES.RedditPassword)
  .toConstantValue(process.env.REDDIT_PASSWORD);
container
  .bind<string>(TYPES.RedditClientID)
  .toConstantValue(process.env.REDDIT_CLIENT_ID);
container
  .bind<string>(TYPES.RedditClientSecret)
  .toConstantValue(process.env.REDDIT_CLIENT_SECRET);

// Initialize channel .env values
container
  .bind<string>(TYPES.LeetcodeChannel)
  .toConstantValue(process.env.LEETCODE_CHANNEL);
container
  .bind<string>(TYPES.AskRedditChannel)
  .toConstantValue(process.env.ASKREDDIT_CHANNEL);

// Initialize Reddit Functionality
container
  .bind<RedditOAuthGenerator>(TYPES.RedditOAuthGenerator)
  .to(RedditOAuthGenerator)
  .inSingletonScope();
container
  .bind<RedditFetchTop>(TYPES.RedditFetchTop)
  .to(RedditFetchTop)
  .inSingletonScope();
container
  .bind<RedditRandomPostGenerator>(TYPES.RedditRandomPostGenerator)
  .to(RedditRandomPostGenerator)
  .inSingletonScope();
container
  .bind<RedditPostParser>(TYPES.RedditPostParser)
  .to(RedditPostParser)
  .inSingletonScope();
container
  .bind<RedditEmbedCreator>(TYPES.RedditEmbedCreator)
  .to(RedditEmbedCreator)
  .inSingletonScope();
container
  .bind<AskRedditFetcher>(TYPES.AskRedditFetcher)
  .to(AskRedditFetcher)
  .inSingletonScope();

// Initialize Leetcode functionality classes
container
  .bind<LeetcodeProblemDownloader>(TYPES.LeetcodeProblemDownloader)
  .to(LeetcodeProblemDownloader)
  .inSingletonScope();
container
  .bind<LeetcodeProblemParser>(TYPES.LeetcodeProblemParser)
  .to(LeetcodeProblemParser)
  .inSingletonScope();
container
  .bind<LeetcodeProblemPicker>(TYPES.LeetcodeProblemPicker)
  .to(LeetcodeProblemPicker)
  .inSingletonScope();
container
  .bind<LeetcodeEmbedCreator>(TYPES.LeetcodeEmbedCreator)
  .to(LeetcodeEmbedCreator)
  .inSingletonScope();
container
  .bind<LeetcodeProblemSelector>(TYPES.LeetcodeProblemSelector)
  .to(LeetcodeProblemSelector)
  .inSingletonScope();

// Initialize Leetcode related dynamic variables
container
  .bind<Set<string>>(TYPES.UsedLeetcodeProblems)
  .toConstantValue(new Set<string>());
container
  .bind<string>(TYPES.LeetcodeProblemFileLocation)
  .toConstantValue("./leetcodeproblems.json");

export default container;
