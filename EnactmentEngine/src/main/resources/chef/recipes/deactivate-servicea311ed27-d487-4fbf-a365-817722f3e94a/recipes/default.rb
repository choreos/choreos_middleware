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

#ruby_block "disable-servicea311ed27-d487-4fbf-a365-817722f3e94a" do
#  block do
#  	# do nothing!
#  	i = 0
#  end
#  only_if { node.run_list.include?("recipe[servicea311ed27-d487-4fbf-a365-817722f3e94a::jar]") }
#  notifies :delete, "file[#{node['CHOReOSData']['serviceData']['a311ed27-d487-4fbf-a365-817722f3e94a']['InstallationDir']}/service-a311ed27-d487-4fbf-a365-817722f3e94a.jar]", :immediately
#end

ruby_block "remove-servicea311ed27-d487-4fbf-a365-817722f3e94a" do
  block do
  	node.run_list.remove("recipe[servicea311ed27-d487-4fbf-a365-817722f3e94a::jar]")
  end
  only_if { node.run_list.include?("recipe[servicea311ed27-d487-4fbf-a365-817722f3e94a::jar]") }
end

ruby_block "remove-deactivate-servicea311ed27-d487-4fbf-a365-817722f3e94a" do
  block do
    node.run_list.remove("recipe[deactivate-servicea311ed27-d487-4fbf-a365-817722f3e94a]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea311ed27-d487-4fbf-a365-817722f3e94a]") }
end
