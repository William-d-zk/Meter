package org.usst.electric.lab.meter.jpa.model.db194e;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public enum PayloadSchema
{
    X1000(0x1000, 4, "A相电压,V"),
    X1002(0x1002, 4, "B相电压,V"),
    X1004(0x1004, 4, "C相电压,V"),
    X1006(0x1006, 4, "A相电流,A"),
    X1008(0x1008, 4, "B相电流,A"),
    X100A(0x100A, 4, "C相电流,A"),
    X100C(0x100C, 4, "零线电流,A"),
    X100E(0x100E, 4, "总有功功率,W"),
    X1010(0x1010, 4, "A相有功功率,W"),
    X1012(0x1012, 4, "B相有功功率,W"),
    X1014(0x1014, 4, "C相有功功率,W"),
    X1016(0x1016, 4, "总无功功率,var"),
    X1018(0x1018, 4, "A相无功功率,var"),
    X101A(0x101A, 4, "B相无功功率,var"),
    X101C(0x101C, 4, "C相无功功率,var"),
    X101E(0x101E, 4, "总视在功率,VA"),
    X1020(0x1020, 4, "A相视在功率,VA"),
    X1022(0x1022, 4, "B相视在功率,VA"),
    X1024(0x1024, 4, "C相视在功率,VA"),
    X1026(0x1026, 4, "总功率因素,0~1.000"),
    X1028(0x1028, 4, "A相功率因素,0~1.000"),
    X102A(0x102A, 4, "B相功率因素,0~1.000"),
    X102C(0x102C, 4, "C相功率因素,0~1.000"),
    X102E(0x102E, 4, "A相线电压,V"),
    X1030(0x1030, 4, "B相线电压,V"),
    X1032(0x1032, 4, "C相线电压,V"),
    X1034(0x1034, 4, "A相频率,Hz"),
    X1036(0x1036, 4, "B相频率,Hz"),
    X1038(0x1038, 4, "C相频率,Hz"),
    X103D(0x103D, 4, "当前总有功电能"),
    X103F(0x103F, 4, "当前总尖有功电能"),
    X1041(0x1041, 4, "当前总峰有功电能"),
    X1043(0x1043, 4, "当前总平有功电能"),
    X1045(0x1045, 4, "当前总谷有功电能"),
    X1047(0x1047, 4, "当前正向总有功电能"),
    X1049(0x1049, 4, "当前正向尖有功电能"),
    X104B(0x104B, 4, "当前正向峰有功电能"),
    X104D(0x104D, 4, "当前正向平有功电能"),
    X104F(0x104F, 4, "当前正向谷有功电能"),
    X1051(0x1051, 4, "当前反向总有功电能"),
    X1053(0x1053, 4, "当前反向尖有功电能"),
    X1055(0x1055, 4, "当前反向峰有功电能"),
    X1057(0x1057, 4, "当前反向平有功电能"),
    X1059(0x1059, 4, "当前反向谷有功电能"),
    X105B(0x105B, 4, "当前总无功电能"),
    X105D(0x105D, 4, "当前尖无功电能"),
    X105F(0x105F, 4, "当前峰无功电能"),
    X1061(0x1061, 4, "当前平无功电能"),
    X1063(0x1063, 4, "当前谷无功电能"),
    X1065(0x1065, 4, "当前正向总无功电能"),
    X1067(0x1067, 4, "当前正向尖无功电能"),
    X1069(0x1069, 4, "当前正向峰无功电能"),
    X106B(0x106B, 4, "当前正向平无功电能"),
    X106D(0x106D, 4, "当前正向谷无功电能"),
    X106F(0x106F, 4, "当前反向总无功电能"),
    X1071(0x1071, 4, "当前反向尖无功电能"),
    X1073(0x1073, 4, "当前反向峰无功电能"),
    X1075(0x1075, 4, "当前反向平无功电能"),
    X1077(0x1077, 4, "当前反向谷无功电能");

    private final int    _Address;
    private final String _Description;
    private final int    _Length;

    PayloadSchema(int addr,
                  int length,
                  String description)
    {
        _Address = addr;
        _Description = description;
        _Length = length;
    }

    public static PayloadSchema addressOf(int addr)
    {
        return switch (addr)
        {
            case 0x1000 -> X1000;
            case 0x1002 -> X1002;
            case 0x1004 -> X1004;
            case 0x1006 -> X1006;
            case 0x1008 -> X1008;
            case 0x100A -> X100A;
            case 0x100C -> X100C;
            case 0x100E -> X100E;
            case 0x1010 -> X1010;
            case 0x1012 -> X1012;
            case 0x1014 -> X1014;
            case 0x1016 -> X1016;
            case 0x1018 -> X1018;
            case 0x101A -> X101A;
            case 0x101C -> X101C;
            case 0x101E -> X101E;
            case 0x1020 -> X1020;
            case 0x1022 -> X1022;
            case 0x1024 -> X1024;
            case 0x1026 -> X1026;
            case 0x1028 -> X1028;
            case 0x102A -> X102A;
            case 0x102C -> X102C;
            case 0x102E -> X102E;
            case 0x1030 -> X1030;
            case 0x1032 -> X1032;
            case 0x1034 -> X1034;
            case 0x1036 -> X1036;
            case 0x1038 -> X1038;
            case 0x103D -> X103D;
            case 0x103F -> X103F;
            case 0x1041 -> X1041;
            case 0x1043 -> X1043;
            case 0x1045 -> X1045;
            case 0x1047 -> X1047;
            case 0x1049 -> X1049;
            case 0x104B -> X104B;
            case 0x104D -> X104D;
            case 0x104F -> X104F;
            case 0x1051 -> X1051;
            case 0x1053 -> X1053;
            case 0x1055 -> X1055;
            case 0x1057 -> X1057;
            case 0x1059 -> X1059;
            case 0x105B -> X105B;
            case 0x105D -> X105D;
            case 0x105F -> X105F;
            case 0x1061 -> X1061;
            case 0x1063 -> X1063;
            case 0x1065 -> X1065;
            case 0x1067 -> X1067;
            case 0x1069 -> X1069;
            case 0x106B -> X106B;
            case 0x106D -> X106D;
            case 0x106F -> X106F;
            case 0x1071 -> X1071;
            case 0x1073 -> X1073;
            case 0x1075 -> X1075;
            case 0x1077 -> X1077;
            default -> throw new IllegalStateException("Unexpected value: " + addr);
        };
    }

    public final byte queryRelay()
    {
        return 0x01;
    }

    public final byte querySwitch()
    {
        return 0x02;
    }

    public final byte queryReg()
    {
        return 0x03;
    }

    public final byte setRelay()
    {
        return 0x05;
    }

    public final byte setMultiRelay()
    {
        return 0x0F;
    }

    public final byte setReg()
    {
        return 0x10;
    }
}
