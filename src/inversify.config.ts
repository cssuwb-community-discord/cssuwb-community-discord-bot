import "reflect-metadata";
import { Container } from "inversify";
import { TYPES } from "./types";
import { Bot } from "./bot";
import { Client } from "discord.js";
import { MessageResponder } from "./services/message-responder";
import { PingFinder } from "./services/ping-finder";
import { LeetcodeProblemDownloader } from "./leetcode/leetcode-problem-downloader";
import { LeetcodeProblemParser } from "./leetcode/leetcode-problem-parser";
import { LeetcodeFileLoader } from "./leetcode/leetcode-file-loader";
import { LeetcodeEmbedCreator } from "./leetcode/leetcode-embed-creator";
import { LeetcodeProblemSelector } from "./leetcode/leetcode-problem-selector";
const customenv = require("dotenv").config().parsed;

// Dependency Injection Container
const container = new Container();

container.bind<Bot>(TYPES.Bot).to(Bot).inSingletonScope();
container.bind<Client>(TYPES.Client).toConstantValue(new Client());
container.bind<string>(TYPES.Token).toConstantValue(customenv.DISCORD_TOKEN);

container
  .bind<MessageResponder>(TYPES.MessageResponder)
  .to(MessageResponder)
  .inSingletonScope();
container.bind<PingFinder>(TYPES.PingFinder).to(PingFinder).inSingletonScope();
container
  .bind<LeetcodeProblemDownloader>(TYPES.LeetcodeProblemDownloader)
  .to(LeetcodeProblemDownloader)
  .inSingletonScope();
container
  .bind<LeetcodeProblemParser>(TYPES.LeetcodeProblemParser)
  .to(LeetcodeProblemParser)
  .inSingletonScope();
container
  .bind<LeetcodeFileLoader>(TYPES.LeetcodeFileLoader)
  .to(LeetcodeFileLoader)
  .inSingletonScope();
container
  .bind<LeetcodeEmbedCreator>(TYPES.LeetcodeEmbedCreator)
  .to(LeetcodeEmbedCreator)
  .inSingletonScope();
container
  .bind<LeetcodeProblemSelector>(TYPES.LeetcodeProblemSelector)
  .to(LeetcodeProblemSelector)
  .inSingletonScope();
export default container;
