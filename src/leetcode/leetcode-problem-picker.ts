import { inject, injectable } from "inversify";
@injectable()
export class LeetcodeProblemPicker {
  pickRandomProblem(rawFileObjectListString: string): Object {
      const rawFileObjectList: Object[] = JSON.parse(
        rawFileObjectListString
      );
      const rawFileObjectListLength: number = rawFileObjectList.length;
      let rawFileObject: Object;
      do {
        const randomIndex: number = Math.floor(
          Math.random() * rawFileObjectListLength
        );
        rawFileObject = rawFileObjectList[randomIndex];
      } while (rawFileObject["paid"]);
      return rawFileObject;
  }
}
