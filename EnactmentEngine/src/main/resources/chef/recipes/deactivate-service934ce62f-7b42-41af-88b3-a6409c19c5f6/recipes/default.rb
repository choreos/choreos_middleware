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

#ruby_block "disable-service934ce62f-7b42-41af-88b3-a6409c19c5f6" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service934ce62f-7b42-41af-88b3-a6409c19c5f6::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['934ce62f-7b42-41af-88b3-a6409c19c5f6']['InstallationDir']}/service-934ce62f-7b42-41af-88b3-a6409c19c5f6.jar]", :immediately
#end

ruby_block "remove-service934ce62f-7b42-41af-88b3-a6409c19c5f6" do
  block do
  	node.run_list.remove("recipe[service934ce62f-7b42-41af-88b3-a6409c19c5f6::jar]")
  end
  only_if { node.run_list.include?("recipe[service934ce62f-7b42-41af-88b3-a6409c19c5f6::jar]") }
end

ruby_block "remove-deactivate-service934ce62f-7b42-41af-88b3-a6409c19c5f6" do
  block do
    node.run_list.remove("recipe[deactivate-service934ce62f-7b42-41af-88b3-a6409c19c5f6]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service934ce62f-7b42-41af-88b3-a6409c19c5f6]") }
end
