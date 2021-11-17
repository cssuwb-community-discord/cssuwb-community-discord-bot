import {Message} from "discord.js";
import {PingFinder} from "./ping-finder";
import {inject, injectable} from "inversify";
import {TYPES} from "../types";
import { AskRedditFetcher } from "../ask-reddit-fetcher";

// Class to handle how bot should respond to valid user input
@injectable()
export class MessageResponder {
  private pingFinder: PingFinder;
  private askRedditFetcher: AskRedditFetcher
  constructor(
    @inject(TYPES.PingFinder) pingFinder: PingFinder,
    @inject(TYPES.AskRedditFetcher) askRedditFetcher: AskRedditFetcher
  ) {
    this.pingFinder = pingFinder;
    this.askRedditFetcher = askRedditFetcher;
  }

  // Directly handle responding to user here
  handle(message: Message): Promise<Message | Message[]> {
    if (this.pingFinder.isPing(message.content)) {
      return message.reply('pong!');
    }
    if(message.content=="!qotd"){
      return this.askRedditFetcher.fetchRedditEmbed()
        .then(embed => message.reply(embed));
    }
    return Promise.reject();
  }
}