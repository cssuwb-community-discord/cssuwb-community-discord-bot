import { MessageEmbed } from "discord.js";
import { inject, injectable } from "inversify";
import { TYPES } from "../types";
import { LeetcodeEmbedCreator } from "./leetcode-embed-creator";
import { LeetcodeProblemPicker } from "./leetcode-problem-picker";
import { LeetcodeProblemParser } from "./leetcode-problem-parser";
import container from "../inversify.config";
import { existsSync, readFileSync } from "fs";
import { LeetcodeProblemDownloader } from "./leetcode-problem-downloader";

@injectable()
export class LeetcodeProblemSelector {
  private problemPicker: LeetcodeProblemPicker;
  private embedCreator: LeetcodeEmbedCreator;
  private problemParser: LeetcodeProblemParser;
  private problemDownloader: LeetcodeProblemDownloader;
  constructor(
    @inject(TYPES.LeetcodeProblemPicker) problemPicker: LeetcodeProblemPicker,
    @inject(TYPES.LeetcodeProblemParser) problemParser: LeetcodeProblemParser,
    @inject(TYPES.LeetcodeEmbedCreator) embedCreator: LeetcodeEmbedCreator,
    @inject(TYPES.LeetcodeProblemDownloader) problemDownloader: LeetcodeProblemDownloader
  ) {
    this.problemPicker = problemPicker;
    this.problemParser = problemParser;
    this.embedCreator = embedCreator;
    this.problemDownloader = problemDownloader;
  }
  selectProblem(): Promise<MessageEmbed> {
    return new Promise<MessageEmbed>(async (resolve, reject) => {
      try {
        console.log(__dirname);
        let savePath:string = container.get<string>(TYPES.LeetcodeProblemFileLocation);
        if(!existsSync(savePath))
        {
          console.log("File doesnt exist, downloading");
          savePath = await this.problemDownloader.downloadParsedProblems();
          container.rebind<string>(TYPES.LeetcodeProblemFileLocation)
            .toConstantValue(savePath);
          console.log(`File saved at ${savePath}`);
          savePath = container.get<string>(TYPES.LeetcodeProblemFileLocation);
        }
        console.log(`Reading file from ${savePath}`)
        const rawFileObjectListString:string = 
          readFileSync(savePath, { encoding: "utf-8" });
        const rawProblemObject = this.problemPicker
          .pickRandomProblem(rawFileObjectListString);
        const parsedProblemObject = this.problemParser
          .parseFileObject(rawProblemObject);
        const embed = this.embedCreator
          .createEmbed(parsedProblemObject)
        resolve(embed);
      }
      catch(exception) {
        reject(exception);
      }
    });
  }
}
