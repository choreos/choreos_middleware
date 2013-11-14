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

#ruby_block "disable-service6837dfa7-dfef-4ff2-a512-222898b803b6" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service6837dfa7-dfef-4ff2-a512-222898b803b6::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['6837dfa7-dfef-4ff2-a512-222898b803b6']['InstallationDir']}/service-6837dfa7-dfef-4ff2-a512-222898b803b6.jar]", :immediately
#end

ruby_block "remove-service6837dfa7-dfef-4ff2-a512-222898b803b6" do
  block do
  	node.run_list.remove("recipe[service6837dfa7-dfef-4ff2-a512-222898b803b6::jar]")
  end
  only_if { node.run_list.include?("recipe[service6837dfa7-dfef-4ff2-a512-222898b803b6::jar]") }
end

ruby_block "remove-deactivate-service6837dfa7-dfef-4ff2-a512-222898b803b6" do
  block do
    node.run_list.remove("recipe[deactivate-service6837dfa7-dfef-4ff2-a512-222898b803b6]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service6837dfa7-dfef-4ff2-a512-222898b803b6]") }
end
