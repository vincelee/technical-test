package xyz.foobar.test;

import org.junit.BeforeClass;
import org.junit.Test;
import xyz.foobar.DifFService;
import xyz.foobar.DiffException;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;


public class DiffServiceTest {

    private static DifFService difFService;

    @BeforeClass
    public static void initDiffService() {
        difFService = new DifFService();
    }


    @Test
    public void scenario1() throws DiffException {

        Person modified = new Person();
        modified.setFirstName("Fred");
        modified.setSurname("Smith");
        modified.setFriend(null);
        modified.setPet(null);
        modified.setNickNames(null);

         assertEquals(6,difFService.calculate(null, modified).size());
         assertEquals("Create: Person",difFService.calculate(null, modified).get(0));
         assertEquals("Create: firstName as Fred",difFService.calculate(null, modified).get(1));
         assertEquals("Create: surname as Smith",difFService.calculate(null, modified).get(2));
         assertEquals("Create: friend as null",difFService.calculate(null, modified).get(3));
         assertEquals("Create: pet as null",difFService.calculate(null, modified).get(4));
         assertEquals("Create: nickNames as null",difFService.calculate(null, modified).get(5));
    }


    @Test
    public void scenario2() throws DiffException {

        Person original = new Person();
        original.setFirstName("Fred");
        original.setSurname("Smith");
        original.setFriend(null);
        original.setPet(null);
        original.setNickNames(null);

        assertEquals(1,difFService.calculate(original, null).size());
        assertEquals("Delete: Person",difFService.calculate(original, null).get(0));
    }


    @Test
    public void scenario3() throws DiffException {

        Person original = new Person();
        original.setFirstName("Fred");
        original.setSurname("Smith");
        original.setFriend(null);
        original.setPet(null);
        original.setNickNames(null);

        Person modified = new Person();
        modified.setFirstName("Fred");
        modified.setSurname("Jones");
        modified.setFriend(null);
        modified.setPet(null);
        modified.setNickNames(null);


        assertEquals(2,difFService.calculate(original, modified).size());
        assertEquals("Update: Person",difFService.calculate(original, modified).get(0));
        assertEquals("Update: surname from Smith to Jones",difFService.calculate(original, modified).get(1));
    }

    @Test
    public void scenario4() throws DiffException {

        Person original = new Person();
        original.setFirstName("Fred");
        original.setSurname("Smith");
        original.setFriend(null);
        original.setPet(null);
        original.setNickNames(null);

        Person friendModified = new Person();
        friendModified.setFirstName("Tom");
        friendModified.setSurname("Brown");
        friendModified.setFriend(null);
        friendModified.setPet(null);
        friendModified.setNickNames(null);

        Person modified = new Person();
        modified.setFirstName("Fred");
        modified.setSurname("Smith");
        modified.setFriend(friendModified);
        modified.setPet(null);
        modified.setNickNames(null);


        assertEquals(8,difFService.calculate(original, modified).size());
        assertEquals("Update: Person",difFService.calculate(original, modified).get(0));
        assertEquals("Update: friend",difFService.calculate(original, modified).get(1));
        assertEquals("Create: Person",difFService.calculate(original, modified).get(2));
        assertEquals("Create: firstName as Tom",difFService.calculate(original, modified).get(3));
        assertEquals("Create: surname as Brown",difFService.calculate(original, modified).get(4));
        assertEquals("Create: friend as null",difFService.calculate(original, modified).get(5));
        assertEquals("Create: pet as null",difFService.calculate(original, modified).get(6));
        assertEquals("Create: nickNames as null",difFService.calculate(original, modified).get(7));
    }

    @Test
    public void scenario5() throws DiffException {

        Pet originalPet = new Pet();
        originalPet.setName("Rover");
        originalPet.setType("Dog");

        Person friendOriginal = new Person();
        friendOriginal.setFirstName("Tom");
        friendOriginal.setSurname("Brown");
        friendOriginal.setFriend(null);
        friendOriginal.setPet(null);
        friendOriginal.setNickNames(null);

        Person original = new Person();
        original.setFirstName("Fred");
        original.setSurname("Smith");
        original.setFriend(friendOriginal);
        original.setPet(originalPet);
        original.setNickNames(null);


        Pet modifiedPet = new Pet();
        modifiedPet.setName("Spot");
        modifiedPet.setType("Dog");

        Person friendModified = new Person();
        friendModified.setFirstName("Jim");
        friendModified.setSurname("Brown");
        friendModified.setFriend(null);
        friendModified.setPet(null);
        friendModified.setNickNames(null);

        Person modified = new Person();
        modified.setFirstName("Fred");
        modified.setSurname("Jones");
        modified.setFriend(friendModified);
        modified.setPet(modifiedPet);
        modified.setNickNames(null);


        assertEquals(6,difFService.calculate(original, modified).size());
        assertEquals("Update: Person",difFService.calculate(original, modified).get(0));
        assertEquals("Update: surname from Smith to Jones",difFService.calculate(original, modified).get(1));
        assertEquals("Update: friend",difFService.calculate(original, modified).get(2));
        assertEquals("Update: firstName from Tom to Jim",difFService.calculate(original, modified).get(3));
        assertEquals("Update: pet",difFService.calculate(original, modified).get(4));
        assertEquals("Update: name from Rover to Spot",difFService.calculate(original, modified).get(5));
    }

    @Test
    public void scenario6() throws DiffException {

        Person friendModified = new Person();
        friendModified.setFirstName("Jim");
        friendModified.setSurname("Brown");
        friendModified.setFriend(null);
        friendModified.setPet(null);
        friendModified.setNickNames(null);

        Person modified = new Person();
        modified.setFirstName("Fred");
        modified.setSurname("Smith");
        modified.setFriend(friendModified);
        modified.setPet(null);
        modified.setNickNames(null);

        Person friendOriginal = new Person();
        friendOriginal.setFirstName("Tom");
        friendOriginal.setSurname("Brown");
        friendOriginal.setFriend(null);
        friendOriginal.setPet(null);
        friendOriginal.setNickNames(null);


        Person original = new Person();
        original.setFirstName("Fred");
        original.setSurname("Smith");
        original.setFriend(friendOriginal);
        original.setPet(null);
        original.setNickNames(null);

        assertEquals(3,difFService.calculate(original, modified).size());
        assertEquals("Update: Person",difFService.calculate(original, modified).get(0));
        assertEquals("Update: friend",difFService.calculate(original, modified).get(1));
        assertEquals("Update: firstName from Tom to Jim",difFService.calculate(original, modified).get(2));

    }

    @Test
    public void scenario7() throws DiffException {

        Person modified = new Person();
        modified.setFirstName("John");
        modified.setSurname("Smith");
        modified.setFriend(null);
        modified.setPet(null);
        modified.setNickNames(null);

        Person friendOriginal = new Person();
        friendOriginal.setFirstName("Tom");
        friendOriginal.setSurname("Brown");
        friendOriginal.setFriend(null);
        friendOriginal.setPet(null);
        friendOriginal.setNickNames(null);


        Person original = new Person();
        original.setFirstName("Fred");
        original.setSurname("Smith");
        original.setFriend(friendOriginal);
        original.setPet(null);
        original.setNickNames(null);

        assertEquals(4,difFService.calculate(original, modified).size());
        assertEquals("Update: Person",difFService.calculate(original, modified).get(0));
        assertEquals("Update: firstName from Fred to John",difFService.calculate(original, modified).get(1));
        assertEquals("Update: friend",difFService.calculate(original, modified).get(2));
        assertEquals("Delete: Person",difFService.calculate(original, modified).get(3));
    }

    @Test
    public void scenario8() throws DiffException {

        Set<String> originalNickNames = new HashSet<>();
        originalNickNames.add("biff");
        originalNickNames.add("scooter");

        Person original = new Person();
        original.setFirstName("Fred");
        original.setSurname("Smith");
        original.setFriend(null);
        original.setPet(null);
        original.setNickNames(originalNickNames);

        Set<String> modifiedNickNames = new HashSet<>();
        modifiedNickNames.add("polly");
        modifiedNickNames.add("biff");

        Person modified = new Person();
        modified.setFirstName("Fred");
        modified.setSurname("Smith");
        modified.setFriend(null);
        modified.setPet(null);
        modified.setNickNames(modifiedNickNames);


        assertEquals(2,difFService.calculate(original, modified).size());
        assertEquals("Update: Person",difFService.calculate(original, modified).get(0));
        assertEquals("Update: nickNames from [biff, scooter] to [polly, biff]",difFService.calculate(original, modified).get(1));
    }

    @Test(expected= DiffException.class)
    public void testExceptionWhenBothOriginalModifiedAreNull() throws DiffException {
        difFService.calculate(null, null);
        assertEquals(1,difFService.calculate(null, null).size());
        assertEquals("Both modified and original cannot be null",difFService.calculate(null, null).get(0));
    }

}
