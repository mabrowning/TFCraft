package com.dunk.tfc.WorldGen.Generators.Trees;

import java.util.Random;

import com.dunk.tfc.Blocks.Flora.BlockBranch;
import com.dunk.tfc.Blocks.Vanilla.BlockCustomLeaves;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.WorldGen.Generators.WorldGenForests;
import com.dunk.tfc.api.TFCBlocks;
import com.dunk.tfc.api.Enums.EnumForestPlant;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCustomBigTree extends WorldGenTreeBase
{

	private final Random rand = new Random();
	float rainfall;
	float temperatureAvg;
	float evt;
	private int tempSourceY;

	public WorldGenCustomBigTree(boolean par1, int id, boolean sap)
	{
		super(par1, id, sap);
	}

	protected boolean generateRandomBranches(World world, Random rand, int xCoord, int yCoord, int zCoord,
			int[] currentDirection, int numBranches, int remainingDistance)
	{
		if (remainingDistance < 1)
		{
			return true;
		}
		boolean[] validDirections = new boolean[] { false, false, false, false, false, false, false, false, false,
				false, false, false, false, false, false, false };

		int numValidDirections = 0;

		if (currentDirection[0] != 1)
		{
			if (currentDirection[2] != 1)
			{
				validDirections[0] = true;
				numValidDirections++;
				validDirections[0 + 8] = true;
				numValidDirections++;
			}
			validDirections[1] = true;
			numValidDirections++;
			validDirections[1 + 8] = true;
			numValidDirections++;
			if (currentDirection[2] != -1)
			{
				validDirections[2] = true;
				numValidDirections++;
				validDirections[2 + 8] = true;
				numValidDirections++;
			}
		}
		if (currentDirection[2] != 1)
		{
			if (currentDirection[0] != -1)
			{
				validDirections[3] = true;
				numValidDirections++;
				validDirections[3 + 8] = true;
				numValidDirections++;
			}
			validDirections[4] = true;
			numValidDirections++;
			validDirections[4 + 8] = true;
			numValidDirections++;
		}
		if (currentDirection[2] != -1)
		{
			if (currentDirection[0] != -1)
			{
				validDirections[5] = true;
				numValidDirections++;
				validDirections[5 + 8] = true;
				numValidDirections++;
			}
			validDirections[6] = true;
			numValidDirections++;
			validDirections[6 + 8] = true;
			numValidDirections++;
		}
		if (currentDirection[0] != -1)
		{
			validDirections[7] = true;
			numValidDirections++;
			validDirections[7 + 8] = true;
			numValidDirections++;
		}
		numBranches = Math.min(numBranches, numValidDirections);
		boolean placedBranch = false;
		Block theBranch = null;
		for (int i = 0; i < numBranches; i++)
		{
			int currentRemainingDistance = remainingDistance;
			if (numValidDirections == 0)
			{
				break;
			}
			int[] curDir = null;

			boolean ignoreBranching = false;
			if (currentRemainingDistance > 1)
			{
				// 50/50 chance whether we keep going straight or start
				// branching.
				ignoreBranching = rand.nextBoolean();
			}

			if (!ignoreBranching)
			{
				int index = rand.nextInt(directions.length);
				while (!validDirections[index])
				{
					index = rand.nextInt(directions.length);
				}
				validDirections[index] = false;
				numValidDirections--;
				curDir = directions[index];
			}
			else
			{
				curDir = currentDirection;
			}

			if (curDir[0] * curDir[2] != 0 || curDir[0] * curDir[1] != 0 || curDir[1] * curDir[2] != 0)
			{
				if (shouldSubtractDistance(rand))
				{
					currentRemainingDistance--;
				}
			}
			if (world.getBlock(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2]).isReplaceable(world,
					xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2]) || world.getBlock(xCoord + curDir[0],
							yCoord + curDir[1], zCoord + curDir[2]) instanceof BlockCustomLeaves)
			{
				theBranch = null;
				if (curDir[1] == 1)
				{
					if (curDir[0] == -1)
					{
						if (curDir[2] == -1)
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_XyZ : TFCBlocks.branchEnd_XyZ;
						}
						else if (curDir[2] == 0)
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_Xy_ : TFCBlocks.branchEnd_Xy_;
						}
						else
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_Xyz : TFCBlocks.branchEnd_Xyz;
						}
					}
					else if (curDir[0] == 0)
					{
						if (curDir[2] == -1)
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch__yZ : TFCBlocks.branchEnd__yZ;
						}
						else
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch__yz : TFCBlocks.branchEnd__yz;
						}
					}
					else
					{
						if (curDir[2] == -1)
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_xyZ : TFCBlocks.branchEnd_xyZ;
						}
						else if (curDir[2] == 0)
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_xy_ : TFCBlocks.branchEnd_xy_;
						}
						else
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_xyz : TFCBlocks.branchEnd_xyz;
						}
					}
				}
				else if (curDir[1] == 0)
				{
					if (curDir[0] == -1)
					{
						if (curDir[2] == -1)
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_X_Z : TFCBlocks.branchEnd_X_Z;
						}
						else if (curDir[2] == 0)
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_X__ : TFCBlocks.branchEnd_X__;
						}
						else
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_X_z : TFCBlocks.branchEnd_X_z;
						}
					}
					else if (curDir[0] == 0)
					{
						if (curDir[2] == -1)
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch___Z : TFCBlocks.branchEnd___Z;
						}
						else
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch___z : TFCBlocks.branchEnd___z;
						}
					}
					else
					{
						if (curDir[2] == -1)
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_x_Z : TFCBlocks.branchEnd_x_Z;
						}
						else if (curDir[2] == 0)
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_x__ : TFCBlocks.branchEnd_x__;
						}
						else
						{
							theBranch = currentRemainingDistance > 1 ? TFCBlocks.branch_x_z : TFCBlocks.branchEnd_x_z;
						}
					}
				}
				if (block2)
				{
					theBranch = TFC_Core.getSecondBranch(theBranch);
				}
				// If the branch directly below is the same, it'll look ugly, so
				// skip it.
				if (world.getBlock(xCoord + curDir[0], yCoord - 1 + curDir[1],
						zCoord + curDir[2]) == theBranch && !ignoreBranching)
				{
					i--;
					continue;
				}
				// We only want to place this branch here if this branch can
				// continue.
				if (generateRandomBranches(world, rand, xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2],
						curDir, 3, currentRemainingDistance - 1))
				{
					world.setBlock(xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2], theBranch, treeId, 2);
					if (((BlockBranch) theBranch).isEnd())
					{
						placeLeaves(world, rand, xCoord + curDir[0], yCoord + curDir[1], zCoord + curDir[2]);
					}
					placedBranch = true;
				}
			}
		}
		return placedBranch;
	}

	@Override
	public boolean placeLeaves(World world, Random random, int xCoord, int yCoord, int zCoord)
	{
		int radius = 2;
		boolean allowVines = (WorldGenForests.shouldPlantGrow(EnumForestPlant.VINE, rainfall, temperatureAvg,
				evt, random));
		boolean lost = BlockBranch.shouldLoseLeaf(world, xCoord, yCoord, zCoord, random, block2);
		boolean defLost = BlockBranch.shouldDefinitelyLoseLeaf(world, xCoord, yCoord, zCoord, block2);
		for (int i = -(radius + 1); i <= (radius + 1); i++)
		{
			for (int j = 0; j <= radius + 1; j++)
			{
				for (int k = -(radius + 1); k <= (radius + 1); k++)
				{
					if (i * i + j * j + k * k <= radius * radius && world.getBlock(xCoord + i, yCoord + j, zCoord + k).isReplaceable(world, xCoord + i, yCoord + j,
							zCoord + k) &&  !defLost && (!lost || random
									.nextInt(3) == 0))
					{												
							world.setBlock(xCoord + i, yCoord + j, zCoord + k, leafBlock, treeId, 2);
							if (!fromSapling && !conversionMatrix[16 + (xCoord + i) - tempSourceX][16 + (zCoord + k) - tempSourceZ])
							{
								convertGrassToDirt(world, xCoord + i, yCoord + j, zCoord + k, height);
								conversionMatrix[16 + (xCoord + i) - tempSourceX][16 + (zCoord + k) - tempSourceZ] = true;
							}
						
					}
					else if(j==0 && world.getBlock(xCoord + i, yCoord + j, zCoord + k).isAir(world, xCoord + i, yCoord + j,
							zCoord + k) && !defLost && allowVines)
					{
						int distFromTrunk_X = tempSourceX-(xCoord+i);
						int distFromTrunk_Z = tempSourceZ-(zCoord+k);
						//Make sure we're not too high and not too close to the trunk
						boolean outerLim = !this.fromSapling && yCoord < this.tempSourceY + this.height + 2 && distFromTrunk_X*distFromTrunk_X + distFromTrunk_Z*distFromTrunk_Z >= radius*radius;
						if (j != 0 && outerLim)
						{
							outerLim = false;
						}
						// If we're on the bottom and the most distant from the
						// trunk
						if (!(outerLim && ((i+1) * (i+1) + k * k <= radius * radius || (i-1) * (i-1) + k * k <= radius * radius ||i*i + (k+1) * (k+1) <=radius * radius || (i) * (i) + (k-1) * (k-1) <= radius * radius )))
						{
							outerLim = false;
						}
						// handle whether we should place vines instead.
						if (outerLim && random.nextInt(10)==0)
						{
						//	world.setBlock(xCoord + i, yCoord + j + 0, zCoord + k, Blocks.cobblestone, 0, 2);
							int meta = 0;
							boolean north_south = Math.abs(k) > Math.abs(i);
							if(north_south)
							{
								meta = k<0?1:4;
							}
							else
							{
								meta = i>=0?2:8;
							}
							world.setBlock(xCoord + i, yCoord + j, zCoord + k, TFCBlocks.vine, meta, 2);
							int dMax = -(rand.nextInt(7) + 2);
							for (int depth = -1; depth > dMax && world
									.getBlock(xCoord + i, yCoord + j + depth, zCoord + k)
									.isAir(world, xCoord + i, yCoord + j + depth, zCoord + k); depth--)
							{
								
								if (world.isAirBlock(xCoord + i, yCoord + j + depth, zCoord + k))
								{
									world.setBlock(xCoord + i, yCoord + j + depth, zCoord + k, TFCBlocks.vine, meta, 2);
								}
								else
								{
									break;
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public boolean generate(World world, Random rand, int x, int y, int z)
	{
		int distFromEdge = 5;
		// We don't want to generate too close to chunk boundaries, because that
		// gets out of hand
		if ((x % 16 > 16 - distFromEdge || Math.abs(
				x) % 16 < distFromEdge || z % 16 > 16 - distFromEdge || Math.abs(z) % 16 < distFromEdge)&&!fromSapling)
		{
			return false;
		}
		long seed = rand.nextLong();
		this.rand.setSeed(seed);
		this.tempSourceX = x;
		this.tempSourceY = y;
		this.tempSourceZ = z;

		conversionMatrix = new boolean[32][32];

		if (!this.validTreeLocation(world, x, y, z))
		{
			return false;
		}
		else
		{
			this.generateTrunk(world, rand, x, y, z);
			return true;
		}
	}

	/**
	 * Places the trunk for the big tree that is being generated. Able to
	 * generate double-sized trunks by changing a field that is always 1 to 2.
	 */
	private void generateTrunk(World world, Random random, int xCoord, int yCoord, int zCoord)
	{

		this.height = random.nextInt(7) + 8;
		this.rainfall = TFC_Climate.getRainfall(world, xCoord, yCoord, zCoord);
		this.temperatureAvg = TFC_Climate.getBioTemperatureHeight(world, xCoord, yCoord, zCoord);
		this.evt = TFC_Climate.getCacheManager(world).getEVTLayerAt(xCoord, zCoord).floatdata1;
		int maxY = yCoord + this.height;
		for (int i = 0; i <= height; i++)
		{
			if (!world.getBlock(xCoord, yCoord + i, zCoord).isReplaceable(world, xCoord, yCoord + i, zCoord))
			{
				break;
			}
		}
		int[] bottom = new int[] { xCoord, yCoord, zCoord };
		int[] top = new int[] { xCoord, maxY, zCoord };

		for (int i = 0; i <= this.height; i++)
		{
			world.setBlock(xCoord, yCoord + i, zCoord, block2 ? TFCBlocks.logNatural2 : TFCBlocks.logNatural, treeId,
					2);
		}

		int zMoss = 0;

		if (zCoord < 0)
			zMoss = -1;
		else
			zMoss = 1;
		float rain = TFC_Climate.getRainfall(world, xCoord, yCoord, zCoord + zMoss);

		boolean mossy = rain > 2000 && !fromSapling;

		boolean veryMossy = rain > 4000 && !fromSapling;

		int mossiness = (int) (rain / 500);
		for (int i = 0; i <= height; i++)
		{
			if (mossy && random.nextInt(10) < mossiness && world.isAirBlock(xCoord, yCoord + i, zCoord + zMoss))
			{
				setBlockAndNotifyAdequately(world, xCoord, yCoord + i, zCoord + zMoss, TFCBlocks.moss, 0);
			}
			if (veryMossy & random.nextInt(10) < mossiness && world.isAirBlock(xCoord + 1, yCoord + i, zCoord))
			{
				setBlockAndNotifyAdequately(world, xCoord + 1, yCoord + i, zCoord, TFCBlocks.moss, 0);
			}
			if (veryMossy & random.nextInt(10) < mossiness && world.isAirBlock(xCoord - 1, yCoord + i, zCoord))
			{
				setBlockAndNotifyAdequately(world, xCoord - 1, yCoord + i, zCoord, TFCBlocks.moss, 0);
			}
		}

		int extraBranchLength = 2;
		// if(treeId == 0)
		// {

		for (int i = 0; i < height/2; i++)
		{
			if (rand.nextInt(i + 1) == 0)
			{
				generateRandomBranches(world, random, xCoord, yCoord + height - i, zCoord, new int[] { -1, 1, -1 }, 1,
						random.nextInt(3) + extraBranchLength + i);
			}
			if (rand.nextInt(i + 1) == 0)
			{
				generateRandomBranches(world, random, xCoord, yCoord + height - i, zCoord, new int[] { 1, 1, -1 }, 1,
						random.nextInt(3) + extraBranchLength + i);
			}
			if (rand.nextInt(i + 1) == 0)
			{
				generateRandomBranches(world, random, xCoord, yCoord + height - i, zCoord, new int[] { 1, 1, 1 }, 1,
						random.nextInt(3) + extraBranchLength + i);
			}
			if (rand.nextInt(i + 1) == 0)
			{
				generateRandomBranches(world, random, xCoord, yCoord + height - i, zCoord, new int[] { -1, 1, 1 }, 1,
						random.nextInt(3) + extraBranchLength + i);
			}
		}
		// }

		/*
		 * if (WorldGenCustomBigTree.trunkSize == 2) { ++var5[0]; ++var6[0];
		 * this.placeBlockLine(var5, var6, TFCBlocks.LogNatural); ++var5[2];
		 * ++var6[2]; this.placeBlockLine(var5, var6, TFCBlocks.LogNatural);
		 * var5[0] += -1; var6[0] += -1; this.placeBlockLine(var5, var6,
		 * TFCBlocks.LogNatural); }
		 */
	}

	/**
	 * Returns a boolean indicating whether or not the current location for the
	 * tree, spanning basePos to to the height limit, is valid.
	 */
	private boolean validTreeLocation(World world, int x, int y, int z)
	{
		if (!(TFC_Core.isSoil(world.getBlock(x, y - 1, z))))
		{
			return false;
		}
		else
		{
			for (int i = 0; i < this.height; i++)
			{
				if (!world.getBlock(x, y + i, z).isReplaceable(world, x, y + i, z))
				{
					return false;
				}
			}
		}
		return true;
	}
}
