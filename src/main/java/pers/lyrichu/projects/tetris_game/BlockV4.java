package pers.lyrichu.projects.tetris_game;

/**
 * 4 * 4 的方块
 */
public class BlockV4 {

	static final boolean[][][] SHAPE = {

			/*
			  o o o o
			 */
			{ 
				{ false, false, false, false }, 
				{ true, true, true, true }, 
				{ false, false, false, false },
				{ false, false, false, false }
			},
			/*
			 * o
			 * o o o
			*/
			{ 
				{ true, false, false },
				{ true, true, true }, 
				{ false, false, false } 
			},
			/*
			 *     o
			 * o o o
			*/
			{ 
				{ false, false, true }, 
				{ true, true, true }, 
				{ false, false, false }
			},
			/*
			 * o o
			 * o o
			*/
			{ 
				{ true, true }, 
				{ true, true } 
			},
			/*
			 *
			 *   o o
			 * o o
			*/
			{ 
				{ false, true, true }, 
				{ true, true, false }, 
				{ false, false, false } 
			},
			/*
			 *   o
			 * o o o
			*/
			{ 
				{ false, true, false }, 
				{ true, true, true }, 
				{ false, false, false } 
			},
			/*
			 * o o
			 *   o o
			*/
			{ 
				{ true, true, false }, 
				{ false, true, true }, 
				{ false, false, false } 
			} };
}
