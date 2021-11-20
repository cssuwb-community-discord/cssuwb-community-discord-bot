import { Message } from "discord.js";
import { PingFinder } from "./ping-finder";
import { inject, injectable } from "inversify";
import { TYPES } from "../types";

// Class to handle how bot should respond to valid user input
@injectable()
export class MessageResponder {
  private pingFinder: PingFinder;

  constructor(@inject(TYPES.PingFinder) pingFinder: PingFinder) {
    this.pingFinder = pingFinder;
  }

  // Directly handle responding to user here
  handle(message: Message): Promise<Message | Message[]> {
    if (this.pingFinder.isPing(message.content)) {
      return message.reply("pong!");
    }

    return Promise.reject();
  }
}
