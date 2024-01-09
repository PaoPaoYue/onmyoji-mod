//package com.github.paopaoyue.onmyojimod.patch;
//
//@SpirePatch(
//  clz=CardCrawlGame.class,
//  method="render"
//)
//public class KamiPatch {
//
//  @SpireInsertPatch(
//    locator=Locator.class,
//    localvars={"sb"}
//  )
//  public static void Insert(CardCrawlGame __instance, SpriteBatch sb) {
//    // draw things right before the SpriteBatch has `end` called
//  }
//
//  // ModTheSpire searches for a nested class that extends SpireInsertLocator
//  // This class will be the Locator for the @SpireInsertPatch
//  // When a Locator is not specified, ModTheSpire uses the default behavior for the @SpireInsertPatch
//  private static class Locator extends SpireInsertLocator {
//    // This is the abstract method from SpireInsertLocator that will be used to find the line
//    // numbers you want this patch inserted at
//    public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
//      // finalMatcher is the line that we want to insert our patch before -
//      // in this example we are using a `MethodCallMatcher` which is a type
//      // of matcher that matches a method call based on the type of the calling
//      // object and the name of the method being called. Here you can see that
//      // we're expecting the `end` method to be called on a `SpireBatch`
//      Matcher finalMatcher = new Matcher.MethodCallMatcher(SpireBatch.class, "end");
//
//      // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
//      // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
//      // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
//      // that matches the finalMatcher.
//      return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
//    }
//  }
//
//}