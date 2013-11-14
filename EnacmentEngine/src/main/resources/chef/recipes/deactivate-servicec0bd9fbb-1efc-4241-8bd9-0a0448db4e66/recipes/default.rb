#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  #
##########################################################################

ruby_block "remove-servicec0bd9fbb-1efc-4241-8bd9-0a0448db4e66" do
  block do
  	node.run_list.remove("recipe[servicec0bd9fbb-1efc-4241-8bd9-0a0448db4e66::jar]")
  end
  only_if { node.run_list.include?("recipe[servicec0bd9fbb-1efc-4241-8bd9-0a0448db4e66::jar]") }
end

ruby_block "remove-deactivate-servicec0bd9fbb-1efc-4241-8bd9-0a0448db4e66" do
  block do
    node.run_list.remove("recipe[deactivate-servicec0bd9fbb-1efc-4241-8bd9-0a0448db4e66]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec0bd9fbb-1efc-4241-8bd9-0a0448db4e66]") }
  notifies :stop, "service[service_c0bd9fbb-1efc-4241-8bd9-0a0448db4e66_jar]", :immediately
end
