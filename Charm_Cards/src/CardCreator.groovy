package net.sf.anathema.charm.cards

import net.sf.anathema.character.generic.magic.*

class CardCreator {

	static void main(String[] args){
		if (args.size() == 2 || args.size() == 1){
			File input = new File(args[0])
			if (args.size() == 2){
				convert(input, new File(args[1]))
				return
			}
			else if (input.isDirectory()){
				processAllFiles(input)
				return
			}
		}
		printUsage()
	}

	static void printUsage(){
		println "Usage:\tjava -jar cardcreator.jar [Inputfile] [Outputfile]"
		println "or\tjava -jar cardcreator.jar [Inputfolder]"
	}

	static void convert(File input, File output){
		ICharm[] charms = new CharmReader().read(input)
		Writer writer = new FileWriter(output)
		new CardsWriter(writer).write charms
		writer.close()
	}

	static File[] processAllFiles(File folder){
		folder.eachFile {
			file -> convert(file, new File(folder, "Cards_" + file.name))
		}
	}
}