package pers.lyrichu.ML.NLP.event_relation;

import	java.util.HashSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventRelationFeatures {

  public static float getWordMatchShare(List<String> words1,List<String> words2) {
    if (words1.isEmpty() || words2.isEmpty())
      return 0;
    float intersectNum = 0;
    for (String word : words1) {
      if (words2.contains(word)) {
        intersectNum++;
      }
    }
    return intersectNum / (words1.size() + words2.size());
  }

  public static float getWordMatchShareUnique(List<String> words1,List<String> words2) {
    if (words1.isEmpty() || words2.isEmpty())
      return 0;
    Set<String> intersectSet = new HashSet<>();
    for (String word : words1) {
      if (words2.contains(word)) {
        intersectSet.add(word);
      }
    }
    Set<String> allSet = new HashSet<>(words1);
    allSet.addAll(words2);
    return (float) intersectSet.size() / allSet.size();
  }


  public static float getIdfWordMatchShare(List<String> words1,List<String> words2,
      Map<String,Double> idfMap) {
    if (words1.isEmpty() || words2.isEmpty())
      return 0;

    float intersectIdfScore = 0;
    for (String word : words1) {
      if (words2.contains(word)) {
        intersectIdfScore += idfMap.getOrDefault(word,0.0);
      }
    }

    float unionIdfScore = 0;

    for (String word : words1) {
      unionIdfScore += idfMap.getOrDefault(word,0.0);
    }

    for (String word : words2) {
      unionIdfScore += idfMap.getOrDefault(word,0.0);
    }

    if (unionIdfScore == 0) {
      return 0;
    }

    return intersectIdfScore / unionIdfScore;
  }


  public static float getIdfWordMatchShareUnique(List<String> words1,List<String> words2,
      Map<String,Double> idfMap) {
    if (words1.isEmpty() || words2.isEmpty())
      return 0;
    Set<String> intersectSet = new HashSet<>();
    for (String word : words1) {
      if (words2.contains(word)) {
        intersectSet.add(word);
      }
    }
    float intersectIdfScore = 0;
    for (String word : intersectSet) {
      intersectIdfScore += idfMap.getOrDefault(word,0.0);
    }
    Set<String> allSet = new HashSet<>(words1);
    allSet.addAll(words2);

    float unionIdfScore = 0;

    for (String word : allSet) {
      unionIdfScore += idfMap.getOrDefault(word,0.0);
    }

    if (unionIdfScore == 0)
      return 0;

    return intersectIdfScore / unionIdfScore;
  }

  public static float getCommonWordsLen(List<String> words1,List<String> words2) {
    float commonCount = 0;

    for (String word : words1) {
      if (words2.contains(word)) {
        commonCount++;
      }
    }
    return commonCount;
  }

  public static float getCommonWordsUniqueLen(List<String> words1,List<String> words2) {
    Set<String> intersectSet = new HashSet<>();
    for (String word : words1) {
      if (words2.contains(word)) {
        intersectSet.add(word);
      }
    }
    return (float) intersectSet.size();
  }

  public static float getWordCountDiff(List<String> words1,List<String> words2) {
    return Math.abs(words1.size() - words2.size());
  }

  public static float getWordCountUniqueDiff(List<String> words1,List<String> words2) {
    return Math.abs(new HashSet<>(words1).size() - new HashSet<>(words2).size());
  }

  public static float getWordCountRatio(List<String> words1,List<String> words2) {
    if (words1.isEmpty() || words2.isEmpty())
      return 0;
    float r = (float) words1.size() / words2.size();
    if (r > 1) {
      return 1/r;
    }
    return r;
  }

  public static float getWordCountUniqueRatio(List<String> words1,List<String> words2) {
    if (words1.isEmpty() || words2.isEmpty())
      return 0;
    float r = (float) new HashSet<>(words1).size() / new HashSet<>(words2).size();
    if (r > 1) {
      return 1/r;
    }
    return r;
  }

  public static float ifSameStartWord(List<String> words1,List<String> words2) {
    if (words1.isEmpty() || words2.isEmpty())
      return 0;
    if (words1.get(0).equalsIgnoreCase(words2.get(0))) {
      return 1;
    }
    return 0;
  }

  public static float getCharDiff(List<String> words1,List<String> words2) {
    int charLen1 = words1.stream().map(String::length).reduce((x,y) -> x + y).get();
    int charLen2 = words2.stream().map(String::length).reduce((x,y) -> x + y).get();

    return (float) Math.abs(charLen1 - charLen2);
  }

  public static float getCharUniqueDiff(List<String> words1,List<String> words2) {
    Set<Character> cs1 = new HashSet<>();
    Set<Character> cs2 = new HashSet<>();
    for (String w : words1) {
      for (int i = 0;i < w.length(); ++i) {
        cs1.add(w.charAt(i));
      }
    }
    for (String w : words2) {
      for (int i = 0;i < w.length(); ++i) {
        cs2.add(w.charAt(i));
      }
    }

    return (float) Math.abs(cs1.size() - cs2.size());
  }

  public static float getCharRatio(List<String> words1,List<String> words2) {
    int charLen1 = words1.stream().map(String::length).reduce((x,y) -> x + y).get();
    int charLen2 = words2.stream().map(String::length).reduce((x,y) -> x + y).get();

    float r = (float) charLen1 / charLen2;
    if (r > 1) {
      return 1/r;
    }
    return r;
  }

  public static float getCharUniqueRatio(List<String> words1,List<String> words2) {
    Set<Character> cs1 = new HashSet<>();
    Set<Character> cs2 = new HashSet<>();
    for (String w : words1) {
      for (int i = 0;i < w.length(); ++i) {
        cs1.add(w.charAt(i));
      }
    }
    for (String w : words2) {
      for (int i = 0;i < w.length(); ++i) {
        cs2.add(w.charAt(i));
      }
    }

    float r = (float) cs1.size() / cs2.size();
    if (r > 1) {
      return 1/r;
    }
    return r;
  }


}
