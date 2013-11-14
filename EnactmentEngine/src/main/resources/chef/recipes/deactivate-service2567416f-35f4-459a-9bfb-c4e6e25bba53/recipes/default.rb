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

ruby_block "remove-service2567416f-35f4-459a-9bfb-c4e6e25bba53" do
  block do
  	# do nothing!
  	i = 0
  end
  only_if { node.run_list.include?("recipe[service2567416f-35f4-459a-9bfb-c4e6e25bba53::jar]") }
  notifies :delete, "file[#{node['tomcat']['webapp_dir']}/2567416f-35f4-459a-9bfb-c4e6e25bba53.war]", :immediately
end

ruby_block "remove-service2567416f-35f4-459a-9bfb-c4e6e25bba53" do
  block do
  	node.run_list.remove("recipe[service2567416f-35f4-459a-9bfb-c4e6e25bba53::war]")
  end
  only_if { node.run_list.include?("recipe[service2567416f-35f4-459a-9bfb-c4e6e25bba53::war]") }
end

ruby_block "remove-deactivate-service2567416f-35f4-459a-9bfb-c4e6e25bba53" do
  block do
    node.run_list.remove("recipe[deactivate-service2567416f-35f4-459a-9bfb-c4e6e25bba53]")
  end
  only_if { node.run_list.include?("recipe[deactivate-service2567416f-35f4-459a-9bfb-c4e6e25bba53]") }
end
