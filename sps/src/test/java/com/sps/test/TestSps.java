package com.sps.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sps.DistanceOptimizer;
import com.sps.IDistanceOptimizer;

public class TestSps {

	private IDistanceOptimizer optimizer = new DistanceOptimizer();

	@Before
	public void setupData() {

		optimizer.addConnection("a", "b", 5d, true);
		optimizer.addConnection("a", "c", 4d, true);
		optimizer.addConnection("a", "f", 8d, true);
		optimizer.addConnection("a", "k", 4d, false);
		optimizer.addConnection("b", "a", 5d, true);
		optimizer.addConnection("b", "c", 2d, true);
		optimizer.addConnection("b", "d", 3d, true);
		optimizer.addConnection("c", "a", 4d, true);
		optimizer.addConnection("c", "b", 2d, true);
		optimizer.addConnection("c", "d", 3d, true);
		optimizer.addConnection("c", "e", 8d, true);
		optimizer.addConnection("d", "b", 3d, true);
		optimizer.addConnection("d", "c", 2d, true);
		optimizer.addConnection("d", "f", 2d, true);
		optimizer.addConnection("e", "c", 8d, true);
		optimizer.addConnection("e", "f", 1d, true);
		optimizer.addConnection("e", "h", 5d, true);
		optimizer.addConnection("f", "a", 8d, true);
		optimizer.addConnection("f", "d", 2d, true);
		optimizer.addConnection("f", "e", 1d, true);
		optimizer.addConnection("f", "g", 6d, true);
		optimizer.addConnection("g", "f", 6d, true);
		optimizer.addConnection("g", "k", 5d, true);
		optimizer.addConnection("h", "i", 8d, true);
		optimizer.addConnection("i", "f", 1d, true);
		optimizer.addConnection("i", "j", 1d, true);
		optimizer.addConnection("j", "g", 3d, true);
		optimizer.addConnection("j", "k", 4d, true);
		optimizer.addConnection("k", "f", 6d, true);
		optimizer.addConnection("z", "z", 1d, true);
	}

	@Test
	public void testShortDistanceGH() {

		assertEquals(12d, optimizer.computeShortestDistance("g", "h"), 0.00001d);

	}

	@Test
	public void testShortDistanceZA() {

		assertEquals(-9999d, optimizer.computeShortestDistance("z", "a"),
				0.00001d);

	}

	@Test
	public void testShortDistanceBE() {

		assertEquals(6d, optimizer.computeShortestDistance("b", "e"), 0.00001d);

	}

	@Test
	public void testShortDistanceAK() {

		assertEquals(4d, optimizer.computeShortestDistance("a", "k"), 0.00001d);

	}

	@Test
	public void testShortDistanceIA() {

		assertEquals(9d, optimizer.computeShortestDistance("i", "a"), 0.00001d);

	}

	@Test
	public void testShortDistanceAI() {

		assertEquals(9d, optimizer.computeShortestDistance("a", "i"), 0.00001d);

	}

}
