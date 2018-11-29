package pers.lyrichu.java.advance.effective_java;

import org.junit.Test;

import static org.junit.Assert.*;

public class BuilderModeTest {
    @Test
    public void builderModeTest() {
        BuilderMode bm = new BuilderMode.Builder(0,"lyrichu")
                .age(20).sex(true).married(false).build();
        assertEquals(bm.getId(),0);
        assertEquals(bm.getName(),"lyrichu");
        assertEquals(bm.getAge(),20);
        assertTrue(bm.isSex());
        assertFalse(bm.isMarried());
    }
}