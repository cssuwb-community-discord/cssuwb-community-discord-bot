import { inject, injectable } from "inversify";
import container from "../inversify.config";
import {TYPES} from "../types";
@injectable()
export class LeetcodeProblemPicker {
  pickRandomProblem(rawFileObjectListString: string): Object {
      const rawFileObjectList: Object[] = JSON.parse(
        rawFileObjectListString
      );
      const rawFileObjectListLength: number = rawFileObjectList.length;
      const currentUsedProblems: Set<string> = container
        .get<Set<string>>(TYPES.UsedLeetcodeProblems);
      let rawFileObject: Object;
      do {
        const randomIndex: number = Math.floor(
          Math.random() * rawFileObjectListLength
        );
        rawFileObject = rawFileObjectList[randomIndex];
      } while (rawFileObject["paid"] || currentUsedProblems.has(
        rawFileObject["problemNameSlug"]));
      currentUsedProblems.add(rawFileObject["problemNameSlug"]);
      container.rebind<Set<string>>(TYPES.UsedLeetcodeProblems)
        .toConstantValue(currentUsedProblems);
      return rawFileObject;
  }
}
