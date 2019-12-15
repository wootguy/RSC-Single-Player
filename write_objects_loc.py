import json, os

data_path = 'cache/data/'

object_defs = {}
object_locs = {}

with open(os.path.join(data_path, 'object_def.json')) as json_file:
    object_defs = json.load(json_file)
with open(os.path.join(data_path, 'object_loc.json')) as json_file:
    object_locs = json.load(json_file)
	
tree_ids = []
	
for d in object_defs:
	if d['modelName'] in ['tree', 'tree2', 'deadtree1']:
		id = d['id'] - 1
		tree_ids.append(id)
		print("" + d['modelName'] + " = %s" % id)
	
new_loc = []
tree_count = 0
other_count = 0
with open('objects.as', 'w') as file:
	for loc in object_locs:	
		if loc['type'] != 1 and loc['id'] in tree_ids:		
			tree_count += 1
			str = "\tg_trees.insertLast(Tree(%s, %s, %s, %s, %s));" % (loc['x'], loc['y'], loc['id'], loc['direction'], loc['type'])
			file.write(str + '\n')
			tree_count += 1
		else:
			new_loc.append(loc)
			
with open('object_loc.json', 'w') as outfile:
    json.dump(new_loc, outfile)

print("TOTAL TREES: %s" % tree_count)