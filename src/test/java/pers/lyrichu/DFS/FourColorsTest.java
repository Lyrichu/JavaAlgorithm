package pers.lyrichu.DFS;

import org.junit.Test;

import static org.junit.Assert.*;
import pers.lyrichu.DFS.FourColors;

public class FourColorsTest {

    @Test
    public void solution() {
        FourColors.initArr();
        FourColors.solution(0);
    }

    @Test
    public void initArr() {
        FourColors.initArr();
    }
}