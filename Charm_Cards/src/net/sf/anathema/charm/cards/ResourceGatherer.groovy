package net.sf.anathema.charm.cards

import net.sf.anathema.lib.resources.*

class ResourceGatherer {

	def provider = new MultiSourceStringProvider()
	def directory


	def gather(){
		new File(directory).eachFile {
			file ->
			if (file.name.endsWith('.properties')){
				provider.add(new FileStringProvider(file.path - '.properties', Locale.ENGLISH))
			}
		}
	}

	def supportsKey(def key){
		provider.supportsKey(key)
	}

	public String getString(String key, Object ... arguments){
		provider.getString(key, arguments)
	}
}