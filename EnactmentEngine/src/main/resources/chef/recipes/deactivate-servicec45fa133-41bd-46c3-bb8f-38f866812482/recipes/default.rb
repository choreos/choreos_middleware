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

ruby_block "disable-servicec45fa133-41bd-46c3-bb8f-38f866812482" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicec45fa133-41bd-46c3-bb8f-38f866812482::jar]") }
  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['c45fa133-41bd-46c3-bb8f-38f866812482']['InstallationDir']}/service-c45fa133-41bd-46c3-bb8f-38f866812482.jar]", :immediately
end

ruby_block "remove-servicec45fa133-41bd-46c3-bb8f-38f866812482" do
  block do
  	node.run_list.remove("recipe[servicec45fa133-41bd-46c3-bb8f-38f866812482::jar]")
  end
  only_if { node.run_list.include?("recipe[servicec45fa133-41bd-46c3-bb8f-38f866812482::jar]") }
end

ruby_block "remove-deactivate-servicec45fa133-41bd-46c3-bb8f-38f866812482" do
  block do
    node.run_list.remove("recipe[deactivate-servicec45fa133-41bd-46c3-bb8f-38f866812482]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicec45fa133-41bd-46c3-bb8f-38f866812482]") }
end
