package pers.lyrichu.projects.tetris_game;

/**
 * 6 * 6 方块
 */
public class BlockV6 {
	static final boolean[][][] SHAPE = {

			/*
			 * o o o o o o
			 */
			{ 
				{ false, false, false, false, false, false }, 
				{ false, false, false, false, false, false }, 
				{ true, true, true, true, true, true }, 
				{ false, false, false, false, false, false }, 
				{ false, false, false, false, false, false }, 
				{ false, false, false, false, false, false }
			},
			/*
			 * o o o
			 * o o o
			 */
			{ 
				{ true, true, true },
				{ true, true, true }, 
				{ false, false, false } 
			},
			/*
			 * o o o
			 *     o
			 *     o
			 *     o
			 */
			{ 
				{ true, true, true , false }, 
				{ false, false, true, false }, 
				{ false, false, true, false }, 
				{ false, false, true, false }
			},
			/*
			 * o o o
			 * o
			 * o
			 * o
			 */
			{ 
				{ false, true, true , true }, 
				{ false, true, false, false }, 
				{ false, true, false, false }, 
				{ false, true, false, false }
			},
			/*
			 *   o
			 *   o
			 * o o
			 * o
			 * o
			 */
			{ 
				{ false, false, true, false, false }, 
				{ false, false, true, false, false }, 
				{ false, true, true, false, false }, 
				{ false, true, false, false, false },
				{ false, true, false, false, false }
			},
			/*
			 * o
			 * o
			 * o o
			 *   o
			 *   o
			 */
			{ 
				{ false, true, false, false, false }, 
				{ false, true, false, false, false }, 
				{ false, true, true, false, false }, 
				{ false, false, true, false, false },
				{ false, false, true, false, false }
			},
			/*
			 * o o o
			 *   o
			 *   o
			 *   o
			 */
			{ 
				{ false, true, true , true }, 
				{ false, false, true, false }, 
				{ false, false, true, false }, 
				{ false, false, true, false }
			},
			/*
			 * o o o o o
			 *     o
			 */
			{ 
				{ false, false, false, false, false }, 
				{ true, true, true, true, true }, 
				{ false, false, true, false, false }, 
				{ false, false, true, false, false },
				{ false, false, false, false, false }
			}
			};
}
