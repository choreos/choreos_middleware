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

#ruby_block "disable-service465699fe-2cf4-49ce-b69f-afb938bf7ea4" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service465699fe-2cf4-49ce-b69f-afb938bf7ea4::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['465699fe-2cf4-49ce-b69f-afb938bf7ea4']['InstallationDir']}/service-465699fe-2cf4-49ce-b69f-afb938bf7ea4.jar]", :immediately
#end

ruby_block "remove-service465699fe-2cf4-49ce-b69f-afb938bf7ea4" do
  block do
  	node.run_list.remove("recipe[service465699fe-2cf4-49ce-b69f-afb938bf7ea4::jar]")
  end
  only_if { node.run_list.include?("recipe[service465699fe-2cf4-49ce-b69f-afb938bf7ea4::jar]") }
end

ruby_block "remove-deactivate-service465699fe-2cf4-49ce-b69f-afb938bf7ea4" do
  block do
    node.run_list.remove("recipe[deactivate-service465699fe-2cf4-49ce-b69f-afb938bf7ea4]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service465699fe-2cf4-49ce-b69f-afb938bf7ea4]") }
end
