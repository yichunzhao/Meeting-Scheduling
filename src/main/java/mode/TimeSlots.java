package mode;

public enum TimeSlots {
  CLOCK_O8(8),
  CLOCK_O9(9),
  CLOCK_10(10),
  CLOCK_11(11),
  CLOCK_12(12),
  CLOCK_13(13),
  CLOCK_14(14),
  CLOCK_15(15),
  CLOCK_16(16),
  CLOCK_17(17);

  private int hourMark;

  TimeSlots(int mark) {
    this.hourMark = mark;
  }

  public int getHourMark() {
    return this.hourMark;
  }
}
