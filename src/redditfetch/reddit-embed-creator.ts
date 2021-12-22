import { MessageEmbed } from "discord.js";
import { injectable } from "inversify";
@injectable()
export class RedditEmbedCreator {
  createEmbed(postInformation: Object): Promise<MessageEmbed> {
    return new Promise<MessageEmbed>((resolve, reject) => {
      try {
        let embed: MessageEmbed = new MessageEmbed()
          .setAuthor(postInformation["author"] + " asks")
          .setColor("#ff0000")
          .setTitle(postInformation["title"])
          .setURL(postInformation["url"])
          .setFooter("From " + postInformation["subreddit"]);
        resolve(embed);
      } catch (exception) {
        reject(exception);
      }
    });
  }
}
