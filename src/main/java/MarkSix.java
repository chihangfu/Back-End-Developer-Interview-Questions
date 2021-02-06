import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MarkSix
{
	public static final int NUMBERS = 6;
	public static final int MAX_NUMBER = 49;
	public static List<List<Integer>> ticketList = new ArrayList<>();
	public static HashSet<String> checkNoDuplicate = new HashSet<>();
	private static final Lock lock = new ReentrantLock();
	private final String separator = "_";

	public void sellTicket(List<List<Integer>> ticketList, HashSet<String> checkNoDuplicate, SecureRandom random)
	{
		lock.lock();
		try
		{
			List<Integer> ticket = getNumberList(random);
			String numberString = convert(ticket);
			if (checkNoDuplicate.add(numberString))
			{
				ticketList.add(ticket);
			}
			else
			{
				System.out.printf(
						"%s ticket number is duplicated, try again later.\n", Thread.currentThread().getName());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			System.out.printf(
					"%s unlock.\n", Thread.currentThread().getName());
			lock.unlock();
		}
	}

	public String convert(List<Integer> numberList)
	{
		StringBuilder numberString = new StringBuilder();
		for (Integer number : numberList)
		{
			numberString.append(number).append(separator);
		}
		if (numberString.length() > 0 && numberString.charAt(numberString.length() - 1) == '_')
		{
			numberString = new StringBuilder(numberString.substring(0, numberString.length() - 1));
		}
		//System.out.println(numberString);
		return numberString.toString();
	}

	public List<Integer> getNumberList(SecureRandom random)
	{
		List<Integer> numList = new ArrayList<>(NUMBERS);
		while (numList.size() < NUMBERS)
		{
			int randomNum = random.nextInt(MAX_NUMBER) + 1;
			if (!numList.contains(randomNum))
			{
				numList.add(randomNum);
			}
		}
		Collections.sort(numList);
		System.out.printf(
				"%s numbers are : " + numList + " .\n", Thread.currentThread().getName());
		return numList;
	}
}

class ConcurrentThread extends Thread
{
	MarkSix markSix;

	ConcurrentThread(String name, MarkSix markSix)
	{
		super(name);
		this.markSix = markSix;
	}

	@Override
	public void run()
	{
		System.out.printf(
				"%s starts gen number list\n", Thread.currentThread().getName());
		SecureRandom random = new SecureRandom();
		markSix.sellTicket(MarkSix.ticketList, MarkSix.checkNoDuplicate, random);
	}
}

class Draw extends TimerTask
{
	MarkSix markSix;
	List<List<Integer>> aList;

	Draw(MarkSix markSix, List<List<Integer>> aList)
	{
		this.markSix = markSix;
		this.aList = aList;
	}

	public void run()
	{
		SecureRandom random = new SecureRandom();
		List<Integer> drawResult = markSix.getNumberList(random);
		System.out.println("The draw result " + drawResult);
		for (List<Integer> checkTicketList : aList)
		{
			if (checkTicketList.equals(drawResult))
			{
				System.out.printf(
						"%s " + checkTicketList + " win the draw \n", Thread.currentThread().getName());
				return;
			}
		}
		System.out.printf(
				"%s no one win the draw \n", Thread.currentThread().getName());

	}
}

class SellTicket extends TimerTask
{
	public void run()
	{
		MarkSix markSix = new MarkSix();
		ConcurrentThread t1 = new ConcurrentThread("Thread - 1 ", markSix);
		ConcurrentThread t2 = new ConcurrentThread("Thread - 2 ", markSix);
		ConcurrentThread t3 = new ConcurrentThread("Thread - 3 ", markSix);
		ConcurrentThread t4 = new ConcurrentThread("Thread - 4 ", markSix);
		ConcurrentThread t5 = new ConcurrentThread("Thread - 5 ", markSix);
		ConcurrentThread t6 = new ConcurrentThread("Thread - 6 ", markSix);
		ConcurrentThread t7 = new ConcurrentThread("Thread - 7 ", markSix);
		ConcurrentThread t8 = new ConcurrentThread("Thread - 8 ", markSix);
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();
	}
}

class MainClass
{
	public static void main(String[] args)
	{
		Timer ticketTimer = new Timer();
		ticketTimer.schedule(new SellTicket(), 0, 5000);
		MarkSix markSix = new MarkSix();
		Timer drawTimer = new Timer();
		drawTimer.schedule(new Draw(markSix, MarkSix.ticketList), 0, 10000);
	}
}