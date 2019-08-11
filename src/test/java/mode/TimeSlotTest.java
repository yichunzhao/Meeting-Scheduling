package mode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeSlotTest {

  @Test
  public void getHourMarkClock08_MarkIs8() {
    assertEquals(8, TimeSlot.CLOCK_O8.getHourMark());
  }

  @Test
  public void getHourMarkClock09_MarkIs9() {
    assertEquals(9, TimeSlot.CLOCK_O9.getHourMark());
  }

  @Test
  public void getHourMarkClock10_MarkIs10() {
    assertEquals(10, TimeSlot.CLOCK_10.getHourMark());
  }

  @Test
  public void getHourMarkClock11_MarkIs11() {
    assertEquals(11, TimeSlot.CLOCK_11.getHourMark());
  }

  @Test
  public void getHourMarkClock12_MarkIs12() {
    assertEquals(12, TimeSlot.CLOCK_12.getHourMark());
  }

  @Test
  public void getHourMarkClock13_MarkIs13() {
    assertEquals(13, TimeSlot.CLOCK_13.getHourMark());
  }

  @Test
  public void getHourMarkClock14_MarkIs14() {
    assertEquals(14, TimeSlot.CLOCK_14.getHourMark());
  }

  @Test
  public void getHourMarkClock15_MarkIs15() {
    assertEquals(15, TimeSlot.CLOCK_15.getHourMark());
  }

  @Test
  public void getHourMarkClock16_MarkIs16() {
    assertEquals(16, TimeSlot.CLOCK_16.getHourMark());
  }

  @Test
  public void getHourMarkClock17_MarkIs17() {
    assertEquals(17, TimeSlot.CLOCK_17.getHourMark());
  }

}
