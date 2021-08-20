package org.usst.electric.lab.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.isahl.chess.knight.raft",
                                           "com.isahl.chess.rook.storage",
                                           "com.isahl.chess.pawn.endpoint",
                                           "com.isahl.chess.player.api",
                                           "org.usst.electric.lab.meter"})
public class ApplicationMeter
{
    public static void main(String[] args)
    {
        SpringApplication.run(ApplicationMeter.class, args);
    }
}
