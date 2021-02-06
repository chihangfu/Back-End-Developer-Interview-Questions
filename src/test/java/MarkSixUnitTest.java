import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;


public class MarkSixUnitTest
{

	@Test
	public void testMarkSix_addToListSuccess()
	{
		MarkSix markSix = new MarkSix();
		MarkSix spy = Mockito.spy(markSix);
		SecureRandom random = new SecureRandom();
		List<Integer> numberList = new ArrayList<>();
		numberList.add(1);
		numberList.add(2);
		numberList.add(3);
		numberList.add(4);
		numberList.add(5);
		numberList.add(6);
		Mockito.doReturn(numberList).when(spy).getNumberList(random);
		List<List<Integer>> list = new ArrayList<>();
		HashSet<String> checkDuplicate = new HashSet<>();
		spy.sellTicket(list, checkDuplicate, random);
		Assert.assertEquals(numberList, list.get(0));
	}

	@Test
	public void testMarkSix_addToExistListSuccess()
	{
		MarkSix markSix = new MarkSix();
		MarkSix spy = Mockito.spy(markSix);
		SecureRandom random = new SecureRandom();
		List<Integer> numberList = new ArrayList<>();
		numberList.add(1);
		numberList.add(2);
		numberList.add(3);
		numberList.add(4);
		numberList.add(5);
		numberList.add(6);

		List<Integer> existList = new ArrayList<>();
		existList.add(11);
		existList.add(22);
		existList.add(33);
		existList.add(34);
		existList.add(40);
		existList.add(46);

		Mockito.doReturn(numberList).when(spy).getNumberList(random);
		List<List<Integer>> list = new ArrayList<>();
		list.add(existList);
		HashSet<String> checkDuplicate = new HashSet<>();
		checkDuplicate.add(spy.convert(existList));
		spy.sellTicket(list, checkDuplicate, random);
		List<Integer> result = new ArrayList<>();
		System.out.println(list);
		for (List<Integer> aList : list)
		{
			if (numberList.equals(aList))
			{
				result = aList;
			}
		}
		Assert.assertEquals(numberList, result);
	}

	@Test
	public void testMarkSix_skipDuplicateNumber()
	{
		MarkSix markSix = new MarkSix();
		MarkSix spy = Mockito.spy(markSix);
		SecureRandom random = new SecureRandom();
		List<Integer> numberList = new ArrayList<>();
		numberList.add(1);
		numberList.add(2);
		numberList.add(3);
		numberList.add(4);
		numberList.add(5);
		numberList.add(6);
		Mockito.doReturn(numberList).when(spy).getNumberList(random);
		List<List<Integer>> list = new ArrayList<>();
		list.add(numberList);
		HashSet<String> checkNoDuplicate = new HashSet<>();
		checkNoDuplicate.add(spy.convert(numberList));
		spy.sellTicket(list, checkNoDuplicate, random);
		Assert.assertEquals(1, list.size());
	}


	@Test
	public void testMarkSix_convertSuccess()
	{
		MarkSix markSix = new MarkSix();
		MarkSix spy = Mockito.spy(markSix);
		List<Integer> numberList = new ArrayList<>();
		numberList.add(1);
		numberList.add(2);
		numberList.add(3);
		numberList.add(4);
		numberList.add(5);
		numberList.add(6);
		String expectedResult = "1_2_3_4_5_6";
		String result = spy.convert(numberList);
		Assert.assertEquals(expectedResult, result);
	}

	@Test
	public void testMarkSix_convertUnexpected()
	{
		MarkSix markSix = new MarkSix();
		MarkSix spy = Mockito.spy(markSix);
		List<Integer> numberList = new ArrayList<>();
		numberList.add(1);
		numberList.add(2);
		numberList.add(3);
		numberList.add(4);
		numberList.add(5);
		numberList.add(6);
		String expectedResult = "1_2_3_4_5_6_";
		String result = spy.convert(numberList);
		Assert.assertNotEquals(expectedResult, result);
	}

	@Test
	public void testMarkSix_drawWinner()
	{
		MarkSix markSix = Mockito.mock(MarkSix.class);
		List<Integer> drawResultList = new ArrayList<>();
		drawResultList.add(1);
		drawResultList.add(2);
		drawResultList.add(3);
		drawResultList.add(4);
		drawResultList.add(5);
		drawResultList.add(6);

		List<Integer> contestant = new ArrayList<>();
		contestant.add(10);
		contestant.add(12);
		contestant.add(22);
		contestant.add(31);
		contestant.add(45);
		contestant.add(46);

		List<Integer> contestant1 = new ArrayList<>();
		contestant1.add(1);
		contestant1.add(2);
		contestant1.add(3);
		contestant1.add(4);
		contestant1.add(5);
		contestant1.add(6);

		List<Integer> contestant2 = new ArrayList<>();
		contestant2.add(15);
		contestant2.add(22);
		contestant2.add(3);
		contestant2.add(4);
		contestant2.add(5);
		contestant2.add(2);

		Mockito.when(markSix.getNumberList(any())).thenReturn(drawResultList);
		List<List<Integer>> contestantList = new ArrayList<>();
		contestantList.add(contestant);
		contestantList.add(contestant1);
		contestantList.add(contestant2);
		Draw draw = new Draw(markSix, contestantList);
		draw.run();
	}

	@Test
	public void testMarkSix_drawNoWinner()
	{
		MarkSix markSix = Mockito.mock(MarkSix.class);
		List<Integer> drawResultList = new ArrayList<>();
		drawResultList.add(1);
		drawResultList.add(2);
		drawResultList.add(3);
		drawResultList.add(4);
		drawResultList.add(5);
		drawResultList.add(6);

		List<Integer> contestant = new ArrayList<>();
		contestant.add(10);
		contestant.add(12);
		contestant.add(22);
		contestant.add(31);
		contestant.add(45);
		contestant.add(46);

		List<Integer> contestant1 = new ArrayList<>();
		contestant1.add(3);
		contestant1.add(5);
		contestant1.add(34);
		contestant1.add(41);
		contestant1.add(44);
		contestant1.add(46);

		List<Integer> contestant2 = new ArrayList<>();
		contestant2.add(15);
		contestant2.add(22);
		contestant2.add(3);
		contestant2.add(4);
		contestant2.add(5);
		contestant2.add(2);

		Mockito.when(markSix.getNumberList(any())).thenReturn(drawResultList);
		List<List<Integer>> contestantList = new ArrayList<>();
		contestantList.add(contestant);
		contestantList.add(contestant1);
		contestantList.add(contestant2);
		Draw draw = new Draw(markSix, contestantList);
		draw.run();
	}
}
