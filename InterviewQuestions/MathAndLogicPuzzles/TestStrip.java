import java.util.ArrayList;

class TestStrip {

    private class Bottle {
    private boolean poisoned = false;
    private int id;

    public Bottle(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAsPoisoned() {
        poisoned = true;
    }

    public boolean isPoisoned() {
        return poisoned;
    }
}

    public static int DAYS_FOR_RESULT = 7;
    private ArrayList<ArrayList<Bottle>> dropsByDay = new ArrayList<ArrayList<Bottle>>();
    private int id;

    public TestStrip(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /* Resize list of days/drops to be large enough. */
    private void sizeDropsForDay(int day) {
        while (dropsByDay.size() <= day) {
            dropsByDay.add(new ArrayList<Bottle>());
        }
    }

    /* Add drop from bottle on specific day. */
    public void addDropOnDay(int day, Bottle bottle) {
        sizeDropsForDay(day);
        ArrayList<Bottle> drops = dropsByDay.get(day);
        drops.add(bottle);
    }

    /* Checks if any of the bottles in the set are poisoned. */
    private boolean hasPoison(ArrayList<Bottle> bottles) {
        for (Bottle b : bottles) {
            if (b.isPoisoned()) {
                return true;
            }
        }
        return false;
    }

    /* Gets bottles used in the test DAYS_FOR_RESULT days ago. */
    public ArrayList<Bottle> getLastWeeksBottles(int day) {
        if (day < DAYS_FOR_RESULT) {
            return null;
        }
        return dropsByDay.get(day - DAYS_FOR_RESULT);
    }

    /* Checks for poisoned bottles since before DAYS_FOR_RESULT */
    public boolean isPositiveOnDay(int day) {
        int testDay = day - DAYS_FOR_RESULT;

        if (testDay < 0 || testDay >= dropsByDay.size()) {
            return false;
        }

        for (int d = 0; d <= testDay; d++) {
            ArrayList<Bottle> bottles = dropsByDay.get(d);
            if (hasPoison(bottles)) {
                return true;
            }
        }
        return false;
    }
    
    public static int findPoisonedBottle(ArrayList<Bottle> bottles, ArrayList<TestStrip> strips) {
		int today = 0;
		
		while (bottles.size() > 1 && strips.size() > 0) {
			/* Run tests. */
			runTestSet(bottles, strips, today);
			
			/* Wait for results. */
			today += TestStrip.DAYS_FOR_RESULT;
			
			/* Check results. */
			for (TestStrip strip : strips) {
				if (strip.isPositiveOnDay(today)) {
					bottles = strip.getLastWeeksBottles(today);
					strips.remove(strip);
					break;
				}
			}
		}
	
		if (bottles.size() == 1) {
			System.out.println("Suspected bottle is " + bottles.get(0).getId() + " on day " + today);
			return bottles.get(0).getId();
		}
		return -1;	
	}	
	
	public static void runTestSet(ArrayList<Bottle> bottles, ArrayList<TestStrip> strips, int day) {
		int index = 0;
		for (Bottle bottle : bottles) {
			TestStrip strip = strips.get(index);
			strip.addDropOnDay(day, bottle);
			index = (index + 1) % strips.size();
		}
	}
}
    
