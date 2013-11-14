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

#ruby_block "disable-service769a18ce-e458-4909-b5f8-2feb80731e67" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[service769a18ce-e458-4909-b5f8-2feb80731e67::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['769a18ce-e458-4909-b5f8-2feb80731e67']['InstallationDir']}/service-769a18ce-e458-4909-b5f8-2feb80731e67.jar]", :immediately
#end

ruby_block "remove-service769a18ce-e458-4909-b5f8-2feb80731e67" do
  block do
  	node.run_list.remove("recipe[service769a18ce-e458-4909-b5f8-2feb80731e67::jar]")
  end
  only_if { node.run_list.include?("recipe[service769a18ce-e458-4909-b5f8-2feb80731e67::jar]") }
end

ruby_block "remove-deactivate-service769a18ce-e458-4909-b5f8-2feb80731e67" do
  block do
    node.run_list.remove("recipe[deactivate-service769a18ce-e458-4909-b5f8-2feb80731e67]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service769a18ce-e458-4909-b5f8-2feb80731e67]") }
end
