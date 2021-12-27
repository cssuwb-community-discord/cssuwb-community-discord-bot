import "reflect-metadata";
import "mocha";
import { expect } from "chai";
import { MessageResponder } from "../src/services/message-responder";
import { instance, mock, verify, when } from "ts-mockito";
import { Message } from "discord.js";
import { LeetcodeProblemDownloader } from "../src/leetcode/leetcode-problem-downloader";
import { LeetcodeProblemSelector } from "../src/leetcode/leetcode-problem-selector";
import { AskRedditFetcher } from "../src/redditfetch/ask-reddit-fetcher";

describe("MessageResponder", () => {
  let mockedMessageClass: Message;
  let mockedMessageInstance: Message;
  let mockedAskRedditFetcherInstance: AskRedditFetcher;
  let mockedLeetcodeProblemDownloaderInstance: LeetcodeProblemDownloader;
  let mockedLeetcodeProblemSelectorInstance: LeetcodeProblemSelector;

  let service: MessageResponder;

  beforeEach(() => {
    mockedMessageClass = mock(Message);
    mockedMessageInstance = instance(mockedMessageClass);
    mockedLeetcodeProblemDownloaderInstance = mock(LeetcodeProblemDownloader);
    mockedLeetcodeProblemSelectorInstance = mock(LeetcodeProblemSelector);
    mockedAskRedditFetcherInstance = mock(AskRedditFetcher);

    setMessageContents();
    service = new MessageResponder(
      mockedLeetcodeProblemDownloaderInstance,
      mockedLeetcodeProblemSelectorInstance,
      mockedAskRedditFetcherInstance
    );
  });

  it("should reply", async () => {

    await service.handle(mockedMessageInstance);

    verify(mockedMessageClass.reply("pong!")).once();
  });

  it("should not reply", async () => {

    await service
      .handle(mockedMessageInstance)
      .then(() => {
        // Successful promise is unexpected, so we fail the test
        expect.fail("Unexpected promise");
      })
      .catch(() => {
        // Rejected promise is expected, so nothing happens here
      });

    verify(mockedMessageClass.reply("pong!")).never();
  });

  function setMessageContents() {
    mockedMessageInstance.content = "Non-empty string";
  }
});
