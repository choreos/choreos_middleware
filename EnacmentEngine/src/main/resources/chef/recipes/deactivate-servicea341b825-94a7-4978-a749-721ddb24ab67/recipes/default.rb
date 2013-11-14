#
# Cookbook Name:: generic-jar-service-template
# Recipe:: default
#
# Copyright 2012, YOUR_COMPANY_NAME
#
# All rights reserved - Do Not Redistribute
#

##########################################################################
#                  														 #
#                IMPORTANT DEVELOPMENT NOTICE:                           #
#                  														 #
# All ocurrences of $ NAME must be replaced with the actual service name #
#            before uploading the recipe to the chef-server              #
#                  														 #
##########################################################################

ruby_block "remove-servicea341b825-94a7-4978-a749-721ddb24ab67" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[servicea341b825-94a7-4978-a749-721ddb24ab67::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/a341b825-94a7-4978-a749-721ddb24ab67.war]", :immediately
end

ruby_block "remove-servicea341b825-94a7-4978-a749-721ddb24ab67" do
  block do
  	node.run_list.remove("recipe[servicea341b825-94a7-4978-a749-721ddb24ab67::war]")
  end
  only_if { node.run_list.include?("recipe[servicea341b825-94a7-4978-a749-721ddb24ab67::war]") }
end

ruby_block "remove-deactivate-servicea341b825-94a7-4978-a749-721ddb24ab67" do
  block do
    node.run_list.remove("recipe[deactivate-servicea341b825-94a7-4978-a749-721ddb24ab67]")
  end
  only_if { node.run_list.include?("recipe[deactivate-servicea341b825-94a7-4978-a749-721ddb24ab67]") }
end
