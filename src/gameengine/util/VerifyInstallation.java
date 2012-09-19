package gameengine.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import gameengine.enumm.UNITTYPE;
import gameengine.sound.Sound;
import gameengine.sound.SoundFactory;
import gameengine.sound.SoundServer;

public final class VerifyInstallation {
    private VerifyInstallation() {
    }

    public static final String MAP_DIR = "maps";
    public static final String NET_DIR = "net";
    public static final String GRAPHICS_DIR = "graphics";
    public static final String CW_DIR = "canvaswarfare";
    public static final String SOUNDS_DIR = "sounds";
    public static final String BGSOUNDS_DIR = "background";
    public static final String GAMESOUNDS_DIR = "ingame";
    public static final String ATTACKSOUNDS_DIR = "attacksounds";
    public static final String MOVESOUNDS_DIR = "movesounds";

    public static final String DESCRIPTION_FILE = "description.xml";
    public static final String MAP_FILE = "map.xml";
    public static final String CONFIG_FILE = "config.xml";
    public static final String THUMB_FILE = "thumb.png";
    public static final String EXPLOSION_FILE = "explosion.mp3";

    private static String[] mapDirs;
    private static String[] bgMusic;

    private static String cwBaseDir;
    private static String seperator;
    private static SoundServer soundServer;

    public static void init(final String baseDir, final SoundFactory soundFactory) throws FileNotFoundException {
        initConfiguration(baseDir);
        initSoundServer(soundFactory);
    }

    private static void initConfiguration(final String environmentPath) throws FileNotFoundException {
        seperator = System.getProperty("file.separator");

        cwBaseDir = environmentPath + seperator + CW_DIR;

        mapDirs = getDirs(MAP_DIR);

    }

    private static void initSoundServer(final SoundFactory soundFactory) throws FileNotFoundException {
        final FilenameFilter mp3 = new FilenameFilter() {
            public boolean accept(final File dir, final String filename) {
                return filename.endsWith(".mp3") || filename.endsWith(".wav") || filename.endsWith(".ogg");
            }
        };

        bgMusic = openFile(SOUNDS_DIR, BGSOUNDS_DIR).list(mp3);

        final Map<UNITTYPE, Sound> attackSounds = new HashMap<UNITTYPE, Sound>();
        final Map<UNITTYPE, Sound> moveSounds = new HashMap<UNITTYPE, Sound>();

        final UNITTYPE[] types = new UNITTYPE[] {UNITTYPE.HELI, UNITTYPE.HORWITZER, UNITTYPE.SOLDIER, UNITTYPE.TANK};
        for (final UNITTYPE type : types) {
            final String attackSound = openFile(SOUNDS_DIR, ATTACKSOUNDS_DIR, type.toString() + ".wav").getAbsolutePath();
            attackSounds.put(type, soundFactory.getSound(attackSound));

            final String moveSound = openFile(SOUNDS_DIR, MOVESOUNDS_DIR, type.toString() + ".wav").getAbsolutePath();
            moveSounds.put(type, soundFactory.getSound(moveSound));
        }

        soundServer = new SoundServer(moveSounds, attackSounds);
    }

    private static String[] getDirs(final String dir) throws FileNotFoundException {
        final List<String> dirs = new LinkedList<String>();
        final String[] tmp = openFile(dir).list();

        for (final String path : tmp) {
            if (openFile(dir, path).isDirectory()) {
                dirs.add(path);
            }
        }

        return dirs.toArray(new String[dirs.size()]);
    }

    public static File openFile(final String... folders) throws FileNotFoundException {
        final StringBuilder builder = new StringBuilder();

        builder.append(cwBaseDir);
        builder.append(seperator);
        builder.append(folders[0]);
        for (int i = 1; i < folders.length; i++) {
            builder.append(seperator);
            builder.append(folders[i]);
        }

        return new File(builder.toString());
    }

    public static String[] getMapDirs() {
        return mapDirs.clone();
    }

    public static String[] getBackGroundMusicFiles() {
        return bgMusic;
    }

    public static SoundServer getSoundServer() {
        return soundServer;
    }
}
