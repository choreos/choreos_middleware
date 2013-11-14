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

ruby_block "remove-service012a60dd-09f4-474e-8da3-d65f08dfbe98" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service012a60dd-09f4-474e-8da3-d65f08dfbe98::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/012a60dd-09f4-474e-8da3-d65f08dfbe98.war", :immediately
end

ruby_block "remove-service012a60dd-09f4-474e-8da3-d65f08dfbe98" do
  block do
  	node.run_list.remove("recipe[service012a60dd-09f4-474e-8da3-d65f08dfbe98::jar]")
  end
  only_if { node.run_list.include?("recipe[service012a60dd-09f4-474e-8da3-d65f08dfbe98::jar]") }
end

ruby_block "remove-deactivate-service012a60dd-09f4-474e-8da3-d65f08dfbe98" do
  block do
    node.run_list.remove("recipe[deactivate-service012a60dd-09f4-474e-8da3-d65f08dfbe98]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service012a60dd-09f4-474e-8da3-d65f08dfbe98]") }
end
