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

#ruby_block "disable-service00eb0d89-87e7-44cf-93ce-e4da66196599" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service00eb0d89-87e7-44cf-93ce-e4da66196599::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['00eb0d89-87e7-44cf-93ce-e4da66196599']['InstallationDir']}/service-00eb0d89-87e7-44cf-93ce-e4da66196599.jar]", :immediately
#end

ruby_block "remove-service00eb0d89-87e7-44cf-93ce-e4da66196599" do
  block do
  	node.run_list.remove("recipe[service00eb0d89-87e7-44cf-93ce-e4da66196599::jar]")
  end
  only_if { node.run_list.include?("recipe[service00eb0d89-87e7-44cf-93ce-e4da66196599::jar]") }
end

ruby_block "remove-deactivate-service00eb0d89-87e7-44cf-93ce-e4da66196599" do
  block do
    node.run_list.remove("recipe[deactivate-service00eb0d89-87e7-44cf-93ce-e4da66196599]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service00eb0d89-87e7-44cf-93ce-e4da66196599]") }
end
