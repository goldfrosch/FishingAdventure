package com.goldfrosch.plugin.fishRank;

public enum Rank {
  C(60),B(Rank.C.getRankPercent() + 25),A(Rank.B.getRankPercent() + 10),S(Rank.A.getRankPercent() + 5);

  int rankPercent;

  Rank(int percent){
    this.rankPercent = percent;
  }

  public int getRankPercent(){
    return rankPercent;
  }

}
