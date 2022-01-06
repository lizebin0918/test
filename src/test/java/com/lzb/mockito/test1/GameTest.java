package com.lzb.mockito.test1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class GameTest {

    @Mock
    private Player player;

    /**
     * 需要通过构造方法，外部注入Mock
     */
    @InjectMocks
    private Game game;

    /**
     * 根据名称注入
     */
    @Spy
    private List<String> weapenList = new ArrayList<>();

    @Spy
    private List<String> bList = new ArrayList<>();

    @Test
    public void attackWithSwordTest() throws Exception {
        Mockito.when(player.getWeapon()).thenReturn("Sword");

        weapenList.add("a");
        weapenList.add("b");

        assertEquals(2, game.sizeOfWeapeans());
        assertEquals("Player attack with:Sword", game.attack());
    }

}