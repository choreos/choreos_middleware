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

ruby_block "disable-service5ac79038-56e1-41e1-9906-4b3847e1a098" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service5ac79038-56e1-41e1-9906-4b3847e1a098::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['5ac79038-56e1-41e1-9906-4b3847e1a098']['InstallationDir']}/service-5ac79038-56e1-41e1-9906-4b3847e1a098.jar]", :immediately
end

ruby_block "remove-service5ac79038-56e1-41e1-9906-4b3847e1a098" do
  block do
  	node.run_list.remove("recipe[service5ac79038-56e1-41e1-9906-4b3847e1a098::jar]")
  end
  only_if { node.run_list.include?("recipe[service5ac79038-56e1-41e1-9906-4b3847e1a098::jar]") }
end

ruby_block "remove-deactivate-service5ac79038-56e1-41e1-9906-4b3847e1a098" do
  block do
    node.run_list.remove("recipe[deactivate-service5ac79038-56e1-41e1-9906-4b3847e1a098]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service5ac79038-56e1-41e1-9906-4b3847e1a098]") }
end
