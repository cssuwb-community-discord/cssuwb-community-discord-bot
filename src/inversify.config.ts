import "reflect-metadata";
import {Container} from "inversify";
import {TYPES} from "./types";
import {Bot} from "./bot";
import { Client } from "discord.js";
import { MessageResponder } from "./services/message-responder";
import { PingFinder } from "./services/ping-finder";

// Dependency Injection Container
let container = new Container();

// Initialize Discord Bot objects
container.bind<Bot>(TYPES.Bot).to(Bot).inSingletonScope();
container.bind<Client>(TYPES.Client).toConstantValue(new Client());
container.bind<string>(TYPES.DiscordToken).toConstantValue(process.env.DISCORD_TOKEN);

// Initialize Reddit credentials
container.bind<string>(TYPES.RedditUsername).toConstantValue(process.env.REDDIT_USERNAME);
container.bind<string>(TYPES.RedditPassword).toConstantValue(process.env.REDDIT_PASSWORD);
container.bind<string>(TYPES.RedditClientID).toConstantValue(process.env.REDDIT_CLIENT_ID);
container.bind<string>(TYPES.RedditClientSecret).toConstantValue(process.env.REDDIT_CLIENT_SECRET);

// Initialize Discord Bot functionality classes
container.bind<MessageResponder>(TYPES.MessageResponder).to(MessageResponder).inSingletonScope();
container.bind<PingFinder>(TYPES.PingFinder).to(PingFinder).inSingletonScope();

export default container;