package net.sf.anathema.charm.cards

import net.sf.anathema.character.generic.magic.*

class CardCreator {def propertiesDirectory = './properties'
	def provider

	static void main(String[] args){
		if (args.size() == 2 || args.size() == 1){
			new CardCreator().run(args)
		}
		else{
			printUsage()
		}
	}

	void run(def args){
		provider = new ResourceGatherer(directory: propertiesDirectory)
		provider.gather()
		File input = new File(args[0])
		if (args.size() == 2){
			convert input, new File(args[1])
		} else if (input.isDirectory()){
			processAllFiles input
		} else{
			printUsage()
		}
	}

	static void printUsage(){
		println "Usage:\tjava -jar cardcreator.jar [Inputfile] [Outputfile]"
		println "or\tjava -jar cardcreator.jar [Inputfolder]"
	}

	void convert(File input, File output){
		ICharm[] charms = new CharmReader().read(input)
		Writer writer = new FileWriter(output)
		new CardsWriter(writer: writer, provider: provider).write charms
		writer.close()
	}

	File[] processAllFiles(File folder){
		folder.eachFile {
			file ->
			if (file.name != ".svn"){
				convert(file, new File(folder, "Cards_" + file.name))
			}
		}
	}
}